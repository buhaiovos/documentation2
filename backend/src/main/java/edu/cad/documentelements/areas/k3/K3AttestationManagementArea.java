package edu.cad.documentelements.areas.k3;

import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.QualificationLevel;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.OtherLoad;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.entities.WorkingPlan;
import edu.cad.study.workingplan.WorkingPlanService;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.ToIntFunction;

import static edu.cad.domain.OtherLoadType.ATTESTATION_MANAGEMENT;
import static edu.cad.domain.QualificationLevel.*;
import static java.util.stream.Collectors.toList;

public class K3AttestationManagementArea extends K3OtherStudyLoadArea {
    private static final String BACHELOR_ATTESTATION_MGMT = "#k3(O)attManBach";
    private static final String PRO_MASTER_ATTESTATION_MGMT = "#k3(O)attManProfMas";
    private static final String SCI_MASTER_ATTESTATION_MGMT = "#k3(O)attManSciMas";

    private WorkingPlanService workingPlanService;

    public K3AttestationManagementArea(Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns) {
        super(semesterNumToColumns);

        this.acceptableTokens.add(BACHELOR_ATTESTATION_MGMT);
        this.acceptableTokens.add(PRO_MASTER_ATTESTATION_MGMT);
        this.acceptableTokens.add(SCI_MASTER_ATTESTATION_MGMT);
    }

    @Override
    protected void fill() {
        fillRow(tokenToRow.get(BACHELOR_ATTESTATION_MGMT), BACHELOR);
        fillRow(tokenToRow.get(PRO_MASTER_ATTESTATION_MGMT), PROF_MASTER);
        fillRow(tokenToRow.get(SCI_MASTER_ATTESTATION_MGMT), SCI_MASTER);
    }

    private void fillRow(Row row, QualificationLevel qualificationLevel) {
        OtherLoadInfo otherLoadInfo = getOtherLoadInfo(qualificationLevel);

        semesterNumToColumns.get(otherLoadInfo.getSemester())
                .forEach(column -> column.fill(row, otherLoadInfo));

        otherLoadInfoDao.save(otherLoadInfo);
    }

    private OtherLoadInfo getOtherLoadInfo(QualificationLevel qualificationLevel) {
        List<AcademicGroup> groups = getMatchingGroups(qualificationLevel);

        OtherLoad otherLoad = getOrCreateLoadHeader(qualificationLevel);

        return getOrCreateLoadInfo(qualificationLevel, groups, otherLoad);
    }

    private List<AcademicGroup> getMatchingGroups(QualificationLevel qualificationLevel) {
        ToIntFunction<AcademicGroup> studentsNumExtractor = sourceOfFinancing.studentNumberGetter();

        return workingPlanService.getAll().stream()
                .filter(workingPlan -> Objects.nonNull(workingPlan.getStateCertification()))
                .map(WorkingPlan::getGroups)
                .flatMap(Set::stream)
                .filter(group -> group.getQualification().getId() == qualificationLevel.getDbId()
                        && studentsNumExtractor.applyAsInt(group) > 0)
                .collect(toList());
    }

    private OtherLoad getOrCreateLoadHeader(QualificationLevel qualificationLevel) {
        ObjectOfWork objectOfWork = qualificationLevel.getObjectOfWork();
        return otherLoadDao.findByLoadTypeAndObjectOfWork(ATTESTATION_MANAGEMENT, objectOfWork)
                .orElseGet(() -> createAndSaveOtherLoad(ATTESTATION_MANAGEMENT, objectOfWork));
    }

    private OtherLoadInfo getOrCreateLoadInfo(QualificationLevel qualificationLevel, List<AcademicGroup> groups,
                                              OtherLoad otherLoad) {
        final int semester = getSemesterForQualification(qualificationLevel);
        OtherLoadInfo info = otherLoadInfoDao.findByLoadHeaderAndSemesterAndEducationFormAndSourceOfFinancing(
                otherLoad, semester, educationForm, sourceOfFinancing
        ).orElseGet(() -> createAndSaveNewOtherLoadInfo(semester, groups, "ІПСА", -1, otherLoad));

        info.setGroups(groups); // in case record in db exists but groups were updated

        return info;
    }

    private int getSemesterForQualification(QualificationLevel qualificationLevel) {
        return switch (qualificationLevel) {
            case BACHELOR, SCI_MASTER, MASTER -> 2;
            case SPECIALIST, PROF_MASTER -> 1;
            default -> throw new UnsupportedOperationException(qualificationLevel.name() + " is not supported");
        };
    }
}
