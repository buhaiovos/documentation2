package edu.cad.documentelements.areas.k3;

import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.domain.StaffType;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.OtherLoad;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.entities.Staff;
import edu.cad.study.staff.StaffService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static edu.cad.domain.OtherLoadType.MANAGEMENT;
import static edu.cad.domain.StaffType.DOCTORAL_STUDENT;

@SuppressWarnings("Duplicates")
public class K3DoctoralConsulting extends K3OtherStudyLoadArea {
    private static final String DOCTORAL = "#k3(O)docCon";

    private StaffService staffService;

    public K3DoctoralConsulting(Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns) {
        super(semesterNumToColumns);

        acceptableTokens.add(DOCTORAL);
    }

    @Override
    protected void fill() {
        fill(DOCTORAL_STUDENT, 1);
        fill(DOCTORAL_STUDENT, 2);
    }

    private void fill(StaffType type, int semester) {
        List<Staff> all = staffService.getAll().stream()
                .filter(actor -> actor.getType().equals(type)
                        && actor.getEducationForm().equals(educationForm)
                        && actor.getSourceOfFinancing().equals(sourceOfFinancing))
                .collect(Collectors.toList());

        List<AcademicGroup> mockGroupsForPostgraduates = createGroupsForPostgraduates(all);
        OtherLoad otherLoad = otherLoadDao.findByLoadTypeAndObjectOfWork(MANAGEMENT, type.getObjectOfWork())
                .orElseGet(() -> createAndSaveOtherLoad(MANAGEMENT, type.getObjectOfWork()));

        OtherLoadInfo loadInfo = otherLoadInfoDao.findByLoadHeaderAndSemesterAndEducationFormAndSourceOfFinancing(
                otherLoad, semester, educationForm, sourceOfFinancing
        ).orElseGet(() -> createAndSaveNewOtherLoadInfo(
                semester, null, "ІПСА", -1, otherLoad
        ));

        loadInfo.setGroups(mockGroupsForPostgraduates);

        String token = resolveForActorType(type);
        semesterNumToColumns.get(semester).forEach(
                column -> column.fill(tokenToRow.get(token), loadInfo)
        );

        loadInfo.setGroups(null);

        otherLoadInfoDao.save(loadInfo);
    }

    private String resolveForActorType(StaffType type) {
        if (type == DOCTORAL_STUDENT) {
            return DOCTORAL;
        }
        throw new IllegalArgumentException(type.name() + " is not supported");
    }


    private List<AcademicGroup> createGroupsForPostgraduates(List<Staff> postgraduates) {
        return postgraduates.stream()
                .map(postgraduate -> {
                    AcademicGroup academicGroup = new AcademicGroup();
                    academicGroup.setCipher(postgraduate.getLastName());
                    academicGroup.setBudgetaryStudents(1);
                    return academicGroup;
                })
                .collect(Collectors.toList());
    }
}
