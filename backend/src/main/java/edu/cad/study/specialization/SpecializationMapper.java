package edu.cad.study.specialization;

import edu.cad.entities.Specialization;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

@Component
class SpecializationMapper implements EntityMapper<Specialization, SpecializationDto> {
    public SpecializationDto toResponse(Specialization spec) {
        return new SpecializationDto()
                .setId(spec.getId())
                .setDenotation(spec.getDenotation())
                .setDepartment(spec.getDepartment().getId());
    }

    public Option toOption(Specialization specialization) {
        return new Option(specialization.getDenotation(), specialization.getId());
    }
}
