package edu.cad.study.department;

import edu.cad.entities.Department;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper implements EntityMapper<Department, DepartmentDto> {
    @Override
    public DepartmentDto toResponse(Department e) {
        return new DepartmentDto()
                .setId(e.getId())
                .setDenotation(e.getDenotation());
    }

    @Override
    public DropdownOption toOption(Department e) {
        return new DropdownOption(e.getId(), e.getDenotation());
    }
}
