package edu.cad.study.web.specialization;

import edu.cad.entities.Specialization;
import edu.cad.utils.gson.Option;
import org.springframework.stereotype.Component;

@Component
class SpecializationMapper {
    SpecializationDto toDto(Specialization spec) {
        return new SpecializationDto()
                .setId(0);
    }

    Option toOption(Specialization specialization) {
        return new Option(specialization.getDenotation(), specialization.getId());
    }
}
