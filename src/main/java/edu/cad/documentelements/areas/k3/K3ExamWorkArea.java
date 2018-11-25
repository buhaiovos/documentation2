package edu.cad.documentelements.areas.k3;

import edu.cad.daos.HibernateDao;
import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.QualificationLevel;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.OtherLoad;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.entities.WorkingPlan;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.ToIntFunction;

import static edu.cad.domain.OtherLoadType.EXAM_COMMISSION_DIPLOMA;
import static edu.cad.domain.QualificationLevel.*;
import static java.util.stream.Collectors.toList;

@SuppressWarnings("Duplicates")
public class K3ExamWorkArea extends K3OtherStudyLoadArea {
    private static final String BACHELORS_DEFENCE = "#k3(O)exManDefBach";
    private static final String PROF_MASTERS_DEFENCE = "#k3(O)exManDefProfMas";
    private static final String SCI_MASTERS_DEFENCE = "#k3(O)exManDefSciMas";

    private HibernateDao<WorkingPlan> wpDao = new HibernateDao<>(WorkingPlan.class);

    public K3ExamWorkArea(Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns) {
        super(semesterNumToColumns);

        acceptableTokens.add(BACHELORS_DEFENCE);
        acceptableTokens.add(PROF_MASTERS_DEFENCE);
        acceptableTokens.add(SCI_MASTERS_DEFENCE);
    }

    @Override
    protected void fill() {
        fillRow(tokenToRow.get(BACHELORS_DEFENCE), BACHELOR);
        fillRow(tokenToRow.get(PROF_MASTERS_DEFENCE), PROF_MASTER);
        fillRow(tokenToRow.get(SCI_MASTERS_DEFENCE), SCI_MASTER);
    }

    private void fillRow(Row row, QualificationLevel qualificationLevel) {
        OtherLoadInfo otherLoadInfo = getOtherLoadInfo(qualificationLevel);

        semesterNumToColumns.get(otherLoadInfo.getSemester())
                .forEach(column -> column.fill(row, otherLoadInfo));

        otherLoadInfoDao.update(otherLoadInfo);
    }

    private OtherLoadInfo getOtherLoadInfo(QualificationLevel qualificationLevel) {
        List<AcademicGroup> groups = getMatchingGroups(qualificationLevel);

        OtherLoad otherLoad = getOrCreateLoadHeader(qualificationLevel);

        return getOrCreateLoadInfo(qualificationLevel, groups, otherLoad);
    }

    private List<AcademicGroup> getMatchingGroups(QualificationLevel qualificationLevel) {
        ToIntFunction<AcademicGroup> studentsNumExtractor = sourceOfFinancing.studentNumberGetter();

        return wpDao.getAll().stream()
                .filter(workingPlan -> Objects.nonNull(workingPlan.getStateCertification()))
                .map(WorkingPlan::getGroups)
                .flatMap(Set::stream)
                .filter(group -> group.getQualification().getId() == qualificationLevel.getDbId()
                        && studentsNumExtractor.applyAsInt(group) > 0)
                .collect(toList());
    }

    private OtherLoad getOrCreateLoadHeader(QualificationLevel qualificationLevel) {
        ObjectOfWork objectOfWork = qualificationLevel.getObjectOfWork();
        return otherLoadDao.findByLoadTypeAndWorkObject(EXAM_COMMISSION_DIPLOMA, objectOfWork)
                .orElseGet(() -> createAndSaveOtherLoad(EXAM_COMMISSION_DIPLOMA, objectOfWork));
    }

    private OtherLoadInfo getOrCreateLoadInfo(QualificationLevel qualificationLevel, List<AcademicGroup> groups,
                                              OtherLoad otherLoad) {
        final int semester = getSemesterForQualification(qualificationLevel);
        OtherLoadInfo info = otherLoadInfoDao.findByLoadHeaderAndSemesterAndEducationFormAndFinancialSource(
                otherLoad, semester, educationForm, sourceOfFinancing
        ).orElseGet(() -> createAndSaveNewOtherLoadInfo(semester, groups, "ІПСА", -1, otherLoad));

        info.setGroups(groups); // in case record in db exists but groups were updated

        return info;
    }

    private int getSemesterForQualification(QualificationLevel qualificationLevel) {
        switch (qualificationLevel) {
            case BACHELOR:
            case SCI_MASTER:
            case MASTER:
                return 2;
            case SPECIALIST:
            case PROF_MASTER:
                return 1;
            default:
                throw new UnsupportedOperationException(qualificationLevel.name() + " is not supported");
        }
    }
}
