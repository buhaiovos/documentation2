package edu.cad.study.web.academicgroup;

import edu.cad.entities.AcademicGroup;
import edu.cad.study.web.EntityMapper;
import edu.cad.utils.gson.Option;
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
                .setWorkplan(getWorkingPlan(group));
    }

    public Option toOption(AcademicGroup academicGroup) {
        return new Option(academicGroup.getCipher(), academicGroup.getId());
    }

    private int getWorkingPlan(AcademicGroup group) {
        return requireNonNull(group.getWorkingPlan(), "workingPlan").getId();
    }

    private int getSpecialization(AcademicGroup group) {
        return requireNonNull(group.getSpecialization(), "specialization").getId();
    }

    private int getEducationForm(AcademicGroup group) {
        return requireNonNull(group.getEducationForm(), "educationForm").getId();
    }

    private int getQualification(AcademicGroup group) {
        return requireNonNull(group.getQualification(), "qualification").getId();
    }
}
