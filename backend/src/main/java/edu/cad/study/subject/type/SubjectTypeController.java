package edu.cad.study.subject.type;

import edu.cad.entities.SubjectType;
import edu.cad.study.ActionController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/subject-types")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectTypeController extends ActionController<SubjectType, Integer, SubjectTypeDto> {
    public SubjectTypeController(SubjectTypeService service, SubjectTypeMapper mapper) {
        super(service, mapper);
    }
}
