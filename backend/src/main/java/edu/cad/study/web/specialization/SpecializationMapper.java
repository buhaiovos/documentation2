package edu.cad.study.web.specialization;

import edu.cad.entities.Specialization;
import edu.cad.study.web.EntityMapper;
import edu.cad.utils.gson.Option;
import org.springframework.stereotype.Component;

@Component
class SpecializationMapper implements EntityMapper<Specialization, SpecializationDto> {
    public SpecializationDto toResponse(Specialization spec) {
        return new SpecializationDto()
                .setId(0);
    }

    public Option toOption(Specialization specialization) {
        return new Option(specialization.getDenotation(), specialization.getId());
    }
}
