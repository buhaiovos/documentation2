package edu.cad.study.staff;

import edu.cad.entities.Staff;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class StaffMapper implements EntityMapper<Staff, StaffDto> {
    @Override
    public StaffDto toResponse(Staff e) {
        return new StaffDto()
                .setDegree(e.getDegree())
                .setFirstName(e.getFirstName())
                .setMiddleName(e.getMiddleName())
                .setLastName(e.getLastName())
                .setType(e.getType().name())
                .setEducationFormId(e.getEducationForm().getId())
                .setDegree(e.getDegree())
                .setPosition(e.getPosition());
    }

    @Override
    public DropdownOption toOption(Staff e) {
        return null;
    }
}
