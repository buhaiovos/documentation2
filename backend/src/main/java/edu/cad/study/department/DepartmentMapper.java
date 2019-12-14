package edu.cad.study.department;

import edu.cad.entities.Department;
import edu.cad.study.EntityMapper;
import edu.cad.utils.gson.Option;
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
    public Option toOption(Department e) {
        return new Option(e.getDenotation(), e.getId());
    }
}
