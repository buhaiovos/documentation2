package edu.cad.study.subject.header;

import edu.cad.entities.SubjectHeader;
import edu.cad.study.ActionController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/subject-headers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectHeaderController extends ActionController<SubjectHeader, Integer, SubjectHeaderDto> {
    public SubjectHeaderController(SubjectHeaderService service, SubjectHeaderMapper mapper) {
        super(service, mapper);
    }
}
