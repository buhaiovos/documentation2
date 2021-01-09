package edu.cad.study.academicgroup;

import edu.cad.entities.AcademicGroup;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
class AcademicGroupMapper implements EntityMapper<AcademicGroup, AcademicGroupDto> {

    public AcademicGroupDto toResponse(AcademicGroup group) {
        return new AcademicGroupDto()
                .setId(group.getId())
                .setCipher(group.getCipher())
                .setBudgetaryStudents(group.getBudgetaryStudents())
                .setContractStudents(group.getContractStudents())
                .setStartYear(group.getStartYear())
                .setEducationForm(getEducationForm(group))
                .setQualification(getQualification(group))
                .setSpecialization(getSpecialization(group))
                .setWorkingPlan(getWorkingPlan(group));
    }

    public DropdownOption toOption(AcademicGroup academicGroup) {
        return new DropdownOption(academicGroup.getId(), academicGroup.getCipher());
    }

    private DropdownOption getWorkingPlan(AcademicGroup group) {
        var workingPlan = requireNonNull(group.getWorkingPlan(), "workingPlan");
        return new DropdownOption(workingPlan.getId(), workingPlan.getDenotation());
    }

    private DropdownOption getSpecialization(AcademicGroup group) {
        var specialization = requireNonNull(group.getSpecialization(), "specialization");
        return new DropdownOption(specialization.getId(), specialization.getDenotation());
    }

    private DropdownOption getEducationForm(AcademicGroup group) {
        var educationForm = requireNonNull(group.getEducationForm(), "educationForm");
        return new DropdownOption(educationForm.getId(), educationForm.getDenotation());
    }

    private DropdownOption getQualification(AcademicGroup group) {
        var qualification = requireNonNull(group.getQualification(), "qualification");
        return new DropdownOption(qualification.getId(), qualification.getDenotation());
    }
}
