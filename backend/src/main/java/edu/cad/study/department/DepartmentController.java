package edu.cad.study.department;

import edu.cad.entities.Department;
import edu.cad.study.ActionController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/departments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController extends ActionController<Department, Integer, DepartmentDto> {
    public DepartmentController(DepartmentService service, DepartmentMapper mapper) {
        super(service, mapper);
    }
}
