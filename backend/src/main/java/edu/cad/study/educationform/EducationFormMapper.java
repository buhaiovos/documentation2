package edu.cad.study.educationform;

import edu.cad.entities.EducationForm;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

@Component
class EducationFormMapper implements EntityMapper<EducationForm, EducationFormDto> {

    @Override
    public DropdownOption toOption(EducationForm specialization) {
        return new DropdownOption(specialization.getId(), specialization.getDenotation());
    }

    @Override
    public EducationFormDto toResponse(EducationForm educationForm) {
        return new EducationFormDto()
                .setDenotation(educationForm.getDenotation())
                .setId(educationForm.getId());
    }
}
