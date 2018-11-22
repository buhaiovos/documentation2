package edu.cad.documentelements.areas.k3;

import edu.cad.daos.HibernateDao;
import edu.cad.daos.OtherLoadDao;
import edu.cad.daos.OtherLoadInfoDao;
import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.entities.*;
import edu.cad.services.years.DbYearsService;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static edu.cad.domain.ObjectOfWork.ALL_MASTERS;
import static edu.cad.domain.OtherLoadType.SCI_RESEARCH_INDIVIDUAL;
import static edu.cad.domain.QualificationLevel.*;
import static java.util.stream.Collectors.*;

public class K3ScienceResearchIndividualsArea extends K3OtherStudyLoadArea {
    private static final String FIRST_YEAR_ROW = "#k3(O)ind_1";
    private static final String SECOND_YEAR_ROW = "#k3(O)ind_2";

    private static DbYearsService dbYearsService;
    private EducationForm educationForm;
    private SourceOfFinancing sourceOfFinancing;

    public K3ScienceResearchIndividualsArea(Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns) {
        super(semesterNumToColumns);

        this.acceptableTokens = new HashSet<>();
        this.acceptableTokens.add(FIRST_YEAR_ROW);
        this.acceptableTokens.add(SECOND_YEAR_ROW);
    }

    @Override
    public void fill(Sheet sheet, EducationForm formOfEducation, SourceOfFinancing sourceOfFinancing) {
        this.educationForm = formOfEducation;
        this.sourceOfFinancing = sourceOfFinancing;

        findRowsOnSheet(sheet);
        process(formOfEducation, sourceOfFinancing);
    }

    private void process(EducationForm formOfEducation, SourceOfFinancing sourceOfFinancing) {
        HibernateDao<WorkingPlan> workingPlanDao = new HibernateDao<>(WorkingPlan.class);
        List<WorkingPlan> workingPlansWithSciSubjects = workingPlanDao.getAll().stream()
                .filter(wp -> Objects.nonNull(wp.getScientificResearchSubject()))
                .collect(toList());

        Map<SubjectInfo, List<AcademicGroup>> sciResearchSubjectToGroups = workingPlansWithSciSubjects.stream()
                .collect(groupingBy(WorkingPlan::getScientificResearchSubject,
                        flatMapping(wp -> wp.getGroups().stream(),
                                filtering(group -> formOfEducation.equals(group.getEducationForm()),
                                        toList())))
                );

        fillArea(sciResearchSubjectToGroups);

    }

    private void fillArea(Map<SubjectInfo, List<AcademicGroup>> sciResearchSubjectToGroups) {
        // split into semesters
        Map<Integer, List<AcademicGroup>> semesterToGroups = sciResearchSubjectToGroups.keySet().stream()
                .collect(
                        groupingBy(info -> info.getSemester() % 2,
                                mapping(sciResearchSubjectToGroups::get,
                                        flatMapping(List::stream, filtering(this::hasMasterQualification, toList()))
                                )
                        )
                );
        processSemester(semesterToGroups, 1);
        processSemester(semesterToGroups, 0);
    }

    private void processSemester(Map<Integer, List<AcademicGroup>> semesterToGroups, int semester) {
        if (semesterToGroups.containsKey(semester)) {
            List<AcademicGroup> groupsForSemester = semesterToGroups.get(semester);
            Map<Integer, List<AcademicGroup>> yearToGroupsWhichApplicableInGivenSemester = groupsForSemester.stream()
                    .collect(groupingBy(this::getYearOfEducation));

            List<AbstractOtherLoadColumn> columnsForSemester = semesterNumToColumns.get(semester);

            processYear(yearToGroupsWhichApplicableInGivenSemester, 1, semester, tokenToRow.get(FIRST_YEAR_ROW),
                    columnsForSemester);

            processYear(yearToGroupsWhichApplicableInGivenSemester, 2, semester, tokenToRow.get(SECOND_YEAR_ROW),
                    columnsForSemester);
        }
    }

    private void processYear(Map<Integer, List<AcademicGroup>> yearToGroups, int year, int semester,
                             Row row, List<AbstractOtherLoadColumn> columns) {
        if (yearToGroups.containsKey(year)) {
            ToIntFunction<AcademicGroup> studentsNumExtractor = resolveForSourceOfFinancing(sourceOfFinancing);
            List<AcademicGroup> groupsWhichHaveStudentsOfGivenFinancialSource = yearToGroups.get(year).stream()
                    .filter(group -> studentsNumExtractor.applyAsInt(group) > 0)
                    .collect(Collectors.toList());

            String faculty = "ІПСА";
            final int yearOfEducation = 4 + year;

            var otherLoadDao = new OtherLoadDao();
            OtherLoad persistedOtherLoad =
                    otherLoadDao.findByLoadTypeAndWorkObject(SCI_RESEARCH_INDIVIDUAL, ALL_MASTERS)
                            .orElseGet(() -> {
                                        var otherLoad = new OtherLoad();
                                        otherLoad.setLoadType(SCI_RESEARCH_INDIVIDUAL);
                                        otherLoad.setObjectOfWork(ALL_MASTERS);

                                        otherLoadDao.create(otherLoad);

                                        return otherLoad;
                                    }
                            );

            var otherLoadInfoDao = new OtherLoadInfoDao();
            OtherLoadInfo persistedInfo =
                    otherLoadInfoDao.findByLoadHeaderAndSemesterAndYearAndEducationFromAndFinancialSource(
                            persistedOtherLoad, semester, yearOfEducation, educationForm, sourceOfFinancing)
                            .orElseGet(() -> {
                                var otherLoadInfo = new OtherLoadInfo();
                                otherLoadInfo.setYearOfEducation(yearOfEducation);
                                otherLoadInfo.setFacultyTitle(faculty);
                                otherLoadInfo.setGroups(groupsWhichHaveStudentsOfGivenFinancialSource);
                                otherLoadInfo.setSemester(semester);
                                otherLoadInfo.setLoadHeader(persistedOtherLoad);
                                otherLoadInfo.setEducationForm(educationForm);
                                otherLoadInfo.setSourceOfFinancing(sourceOfFinancing);

                                otherLoadInfoDao.create(otherLoadInfo);

                                return otherLoadInfo;
                            });


            columns.forEach(column -> column.fill(row, persistedInfo));

            otherLoadInfoDao.update(persistedInfo);

        }
    }

    private ToIntFunction<AcademicGroup> resolveForSourceOfFinancing(SourceOfFinancing sourceOfFinancing) {
        switch (sourceOfFinancing) {
            case Contract:
                return AcademicGroup::getContractStudents;
            case Budgetary:
                return AcademicGroup::getBudgetaryStudents;
            default:
                throw new IllegalArgumentException(sourceOfFinancing.name());
        }
    }


    private boolean hasMasterQualification(AcademicGroup group) { // bad design. Answer to this question should be given by AcademicGroup object
        return Set.of(
                MASTER.getDbId(),
                PROF_MASTER.getDbId(),
                SCI_MASTER.getDbId()
        ).contains(
                group.getQualification().getId()
        );
    }

    private Integer getYearOfEducation(AcademicGroup group) {
        return dbYearsService.getCurrent() - group.getStartYear() + 1;
    }

    public static void setDbYearsService(DbYearsService service) {
        dbYearsService = service;
    }
}