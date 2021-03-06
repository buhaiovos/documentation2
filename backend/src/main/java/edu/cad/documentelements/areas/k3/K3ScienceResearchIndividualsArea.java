package edu.cad.documentelements.areas.k3;

import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.entities.*;
import edu.cad.services.years.DbYearsService;
import edu.cad.study.workingplan.WorkingPlanService;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static edu.cad.domain.ObjectOfWork.ALL_MASTERS;
import static edu.cad.domain.OtherLoadType.SCI_RESEARCH_INDIVIDUAL;
import static edu.cad.domain.QualificationLevel.PROF_MASTER;
import static edu.cad.domain.QualificationLevel.SCI_MASTER;
import static java.util.stream.Collectors.*;

public class K3ScienceResearchIndividualsArea extends K3OtherStudyLoadArea {
    private static final String FIRST_YEAR_ROW = "#k3(O)ind_1";
    private static final String SECOND_YEAR_ROW = "#k3(O)ind_2";

    private static DbYearsService dbYearsService;
    private WorkingPlanService workingPlanService;

    public K3ScienceResearchIndividualsArea(Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns) {
        super(semesterNumToColumns);

        this.acceptableTokens.add(FIRST_YEAR_ROW);
        this.acceptableTokens.add(SECOND_YEAR_ROW);
    }

    @Override
    protected void fill() {
        List<WorkingPlan> workingPlansWithSciSubjects = workingPlanService.getAll().stream()
                .filter(wp -> Objects.nonNull(wp.getScientificResearchSubject()))
                .collect(toList());

        Map<SubjectInfo, List<AcademicGroup>> sciResearchSubjectToGroups = workingPlansWithSciSubjects.stream()
                .collect(groupingBy(WorkingPlan::getScientificResearchSubject,
                        flatMapping(wp -> wp.getGroups().stream(),
                                filtering(group -> educationForm.equals(group.getEducationForm()),
                                        toList())))
                );

        fillArea(sciResearchSubjectToGroups);
    }

    private void fillArea(Map<SubjectInfo, List<AcademicGroup>> sciResearchSubjectToGroups) {
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

            processYear(
                    yearToGroupsWhichApplicableInGivenSemester,
                    1,
                    semester,
                    tokenToRow.get(FIRST_YEAR_ROW),
                    semesterNumToColumns.get(semester)
            );

            processYear(
                    yearToGroupsWhichApplicableInGivenSemester,
                    2,
                    semester,
                    tokenToRow.get(SECOND_YEAR_ROW),
                    semesterNumToColumns.get(semester)
            );
        }
    }

    private void processYear(Map<Integer, List<AcademicGroup>> yearToGroups, int year, int semester,
                             Row row, List<AbstractOtherLoadColumn> columns) {
        if (yearToGroups.containsKey(year)) {
            ToIntFunction<AcademicGroup> studentsNumExtractor = sourceOfFinancing.studentNumberGetter();
            List<AcademicGroup> groupsWhichHaveStudentsOfGivenFinancialSource = yearToGroups.get(year).stream()
                    .filter(group -> studentsNumExtractor.applyAsInt(group) > 0)
                    .collect(Collectors.toList());

            String faculty = "ІПСА";
            final int yearOfEducation = 4 + year;

            OtherLoad persistedOtherLoad = otherLoadDao.findByLoadTypeAndObjectOfWork(SCI_RESEARCH_INDIVIDUAL, ALL_MASTERS)
                    .orElseGet(this::createAndSaveOtherLoad);

            OtherLoadInfo persistedInfo = otherLoadInfoDao
                    .findByLoadHeaderAndSemesterAndYearAndEducationFormAndSourceOfFinancing(
                            persistedOtherLoad,
                            semester, yearOfEducation,
                            educationForm,
                            sourceOfFinancing
                    )
                    .orElseGet(
                            () -> createAndSaveNewOtherLoadInfo(
                                    semester, groupsWhichHaveStudentsOfGivenFinancialSource,
                                    faculty, yearOfEducation, persistedOtherLoad
                            )
                    );

            columns.forEach(column -> column.fill(row, persistedInfo));

            otherLoadInfoDao.save(persistedInfo);

        }
    }


    private boolean hasMasterQualification(AcademicGroup group) { // bad design. Answer to this question should be given by AcademicGroup object
        return Set.of(
//                MASTER.getDbId(),
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

    private OtherLoad createAndSaveOtherLoad() {
        return createAndSaveOtherLoad(SCI_RESEARCH_INDIVIDUAL, ALL_MASTERS);
    }

}
