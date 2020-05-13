package edu.cad.study.worktype;

import edu.cad.entities.WorkType;
import edu.cad.study.ActionController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/work-types")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkTypeController extends ActionController<WorkType, Integer, WorkTypeDto> {
    public WorkTypeController(WorkTypeService service, WorkTypeMapper mapper) {
        super(service, mapper);
    }
}
