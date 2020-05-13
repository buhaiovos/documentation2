package edu.cad.study.specialization;

import edu.cad.entities.Specialization;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

@Component
class SpecializationMapper implements EntityMapper<Specialization, SpecializationDto> {
    public SpecializationDto toResponse(Specialization spec) {
        return new SpecializationDto()
                .setId(spec.getId())
                .setDenotation(spec.getDenotation())
                .setDepartment(spec.getDepartment().getId());
    }

    public DropdownOption toOption(Specialization specialization) {
        return new DropdownOption(specialization.getId(), specialization.getDenotation());
    }
}
