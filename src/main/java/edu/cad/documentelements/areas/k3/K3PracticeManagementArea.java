package edu.cad.documentelements.areas.k3;

import edu.cad.daos.HibernateDao;
import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.PracticeType;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.OtherLoad;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.entities.WorkingPlan;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static edu.cad.domain.OtherLoadType.PRACTICE_MANAGEMENT;
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
        getLoadsPerSemester(practiceType)
                .entrySet()
                .forEach(entry -> fillAndUpdateInDb(entry, row));
    }

    private Map<Integer, OtherLoadInfo> getLoadsPerSemester(PracticeType practiceType) {
        List<WorkingPlan> workingPlansWithGivenPractice = getWorkingPlansByPracticeType(practiceType);

        Map<Integer, List<AcademicGroup>> semesterToGroups =
                getGroupsInvolvedGroupedBySemester(workingPlansWithGivenPractice);

        return semesterToGroups.entrySet().stream()
                .map(entry -> toSemesterLoadInfoPair(entry, practiceType))
                .filter(Objects::nonNull)
                .collect(toMap(Pair::getLeft, Pair::getRight));
    }

    private void fillAndUpdateInDb(Map.Entry<Integer, OtherLoadInfo> entry, Row row) {
        OtherLoadInfo info = entry.getValue();
        semesterNumToColumns.get(entry.getKey()).forEach(column -> column.fill(row, info));
        otherLoadInfoDao.update(info);
    }

    private List<WorkingPlan> getWorkingPlansByPracticeType(PracticeType practiceType) {
        HibernateDao<WorkingPlan> wpDao = new HibernateDao<>(WorkingPlan.class);

        return wpDao.getAll().stream()
                .filter(wp -> Objects.nonNull(wp.getPractice()))
                .filter(wp -> wp.getPractice().getType().equals(practiceType))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<AcademicGroup>> getGroupsInvolvedGroupedBySemester(List<WorkingPlan> workingPlansWithGivenPractice) {
        ToIntFunction<AcademicGroup> studentsNumExtractor = resolveForSourceOfFinancing(sourceOfFinancing);
        return workingPlansWithGivenPractice.stream()
                .collect(
                        groupingBy(this::calculateSemesterForPractice,
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

    private Integer calculateSemesterForPractice(WorkingPlan wp) {
        int semester = wp.getPractice().getSemester();
        return semester % 2 == 0 ? 2 : 1;
    }

    private Pair<Integer, OtherLoadInfo> toSemesterLoadInfoPair(Map.Entry<Integer, List<AcademicGroup>> entry,
                                                                PracticeType practiceType) {
        if (!entry.getValue().isEmpty()) {
            return getOtherLoadPairedWithSemester(entry, practiceType);
        }
        return null;
    }

    private Pair<Integer, OtherLoadInfo> getOtherLoadPairedWithSemester(Map.Entry<Integer, List<AcademicGroup>> entry,
                                                                        PracticeType practiceType) {
        OtherLoad header = findOrCreateLoadHeader(practiceType);

        final Integer semester = entry.getKey();
        OtherLoadInfo info = findOrCreateOtherLoadInfo(header, semester, entry.getValue());

        return new Pair<>(semester, info);
    }

    private OtherLoad findOrCreateLoadHeader(PracticeType practiceType) {
        final ObjectOfWork objectOfWork = practiceType.getObjectOfWork();
        return otherLoadDao.findByLoadTypeAndWorkObject(PRACTICE_MANAGEMENT, objectOfWork)
                .orElseGet(() -> createAndSaveOtherLoad(PRACTICE_MANAGEMENT, objectOfWork));
    }

    private OtherLoadInfo findOrCreateOtherLoadInfo(OtherLoad header, Integer semester, List<AcademicGroup> groups) {
        OtherLoadInfo info = otherLoadInfoDao.findByLoadHeaderAndSemesterAndEducationFormAndFinancialSource(
                header, semester, educationForm, sourceOfFinancing
        ).orElseGet(
                () -> createAndSaveNewOtherLoadInfo(semester, groups, "IPSA", -1, header)
        );

        info.setGroups(groups); //in case this changed in data but in db values are not up-to-date

        return info;
    }

    @RequiredArgsConstructor
    @Getter
    private static final class Pair<T1, T2> {
        private final T1 left;
        private final T2 right;
    }
}
