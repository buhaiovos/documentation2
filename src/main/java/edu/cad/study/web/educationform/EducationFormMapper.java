package edu.cad.study.web.educationform;

import edu.cad.entities.EducationForm;
import edu.cad.utils.gson.Option;
import org.springframework.stereotype.Component;

@Component
class EducationFormMapper {
    EducationFormDto toDto(EducationForm spec) {
        return new EducationFormDto()
                .setId(0);
    }

    Option toOption(EducationForm specialization) {
        return new Option(specialization.getDenotation(), specialization.getId());
    }
}
