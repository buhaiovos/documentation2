package edu.cad.study.educationform;

import edu.cad.entities.EducationForm;
import edu.cad.study.EntityMapper;
import edu.cad.utils.gson.Option;
import org.springframework.stereotype.Component;

@Component
class EducationFormMapper implements EntityMapper<EducationForm, EducationFormDto> {

    @Override
    public Option toOption(EducationForm specialization) {
        return new Option(specialization.getDenotation(), specialization.getId());
    }

    @Override
    public EducationFormDto toResponse(EducationForm educationForm) {
        return new EducationFormDto()
                .setDenotation(educationForm.getDenotation())
                .setId(educationForm.getId());
    }
}
