package edu.cad.study.staff;

import edu.cad.entities.Staff;
import edu.cad.study.ActionController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/staff")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffController extends ActionController<Staff, Integer, StaffDto> {
    public StaffController(StaffService service, StaffMapper mapper) {
        super(service, mapper);
    }
}
