package edu.cad.documentelements.areas.k3;

import edu.cad.daos.HibernateDao;
import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.domain.PracticeType;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.EducationForm;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.entities.WorkingPlan;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.ss.usermodel.Row;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static edu.cad.domain.PracticeType.*;
import static java.util.stream.Collectors.*;

public class K3PracticeManagementArea extends K3OtherStudyLoadArea {
    private static final String PRE_DIPLOMA_PRACTICE_TOKEN = "#k3(O)practPreDip";
    private static final String SCI_RESEARCH_PRACTICE_TOKEN = "#k3(O)practSciRes";
    private static final String PEDAGOGICAL_PRACTICE_TOKEN = "#k3(O)practPedagogic";

    public K3PracticeManagementArea(Map<Integer, List<AbstractOtherLoadColumn>> semesterToColumns) {
        super(semesterToColumns);

        this.acceptableTokens.add(PRE_DIPLOMA_PRACTICE_TOKEN);
        this.acceptableTokens.add(SCI_RESEARCH_PRACTICE_TOKEN);
        this.acceptableTokens.add(PEDAGOGICAL_PRACTICE_TOKEN);
    }

    @Override
    protected void fill() {
        fillPreDiplomaPractice(this.tokenToRow.get(PRE_DIPLOMA_PRACTICE_TOKEN));
        fillScientificResearchPractice(this.tokenToRow.get(SCI_RESEARCH_PRACTICE_TOKEN));
        fillPedagogicalPractice(this.tokenToRow.get(PEDAGOGICAL_PRACTICE_TOKEN));
    }

    private void fillPreDiplomaPractice(Row row) {
        fillArea(row, PRE_DIPLOMA);
    }

    private void fillScientificResearchPractice(Row row) {
        fillArea(row, SCI_RESEARCH);
    }

    private void fillPedagogicalPractice(Row row) {
        fillArea(row, PEDAGOGICAL);
    }

    private void fillArea(Row row, PracticeType practiceType) {
        getLoadsPerSemester(sourceOfFinancing, educationForm, practiceType)
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent())
                .forEach(
                        entry -> semesterNumToColumns.get(entry.getKey()).forEach(column -> column.fill(row, entry.getValue().get()))
                );
    }

    private Map<Integer, Optional<OtherLoadInfo>> getLoadsPerSemester(SourceOfFinancing sourceOfFinancing,
                                                                      EducationForm educationForm,
                                                                      PracticeType practiceType) {
        List<WorkingPlan> workingPlansWithGivenPractice = getWorkingPlansByPracticeType(practiceType);

        Map<Integer, List<AcademicGroup>> semesterToGroups =
                getGroupsInvolvedGroupedBySemester(sourceOfFinancing, educationForm, workingPlansWithGivenPractice);


        throw new UnsupportedOperationException();
    }

    private Map<Integer, List<AcademicGroup>> getGroupsInvolvedGroupedBySemester(SourceOfFinancing sourceOfFinancing,
                                                                                 EducationForm educationForm,
                                                                                 List<WorkingPlan> workingPlansWithGivenPractice) {
        ToIntFunction<AcademicGroup> studentsNumExtractor = resolveForSourceOfFinancing(sourceOfFinancing);
        return workingPlansWithGivenPractice.stream()
                .collect(
                        groupingBy(wp -> wp.getPractice().getSemester(),
                                mapping(WorkingPlan::getGroups,
                                        flatMapping(Set::stream,
                                                filtering(
                                                        group -> group.getEducationForm().equals(educationForm)
                                                                && studentsNumExtractor.applyAsInt(group) > 0,
                                                        toList()
                                                )
                                        )
                                )
                        )
                );
    }

    private List<WorkingPlan> getWorkingPlansByPracticeType(PracticeType practiceType) {
        HibernateDao<WorkingPlan> wpDao = new HibernateDao<>(WorkingPlan.class);

        return wpDao.getAll().stream()
                .filter(wp -> Objects.nonNull(wp.getPractice()))
                .filter(wp -> wp.getPractice().getType().equals(practiceType))
                .collect(Collectors.toList());
    }
}
