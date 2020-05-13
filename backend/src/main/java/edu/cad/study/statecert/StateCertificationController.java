package edu.cad.study.statecert;

import edu.cad.entities.StateCertification;
import edu.cad.study.ActionController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/state-certifications")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StateCertificationController extends ActionController<StateCertification, Integer, StateCertificationDto> {
    public StateCertificationController(StateCertificationService service, StateCertificationMapper mapper) {
        super(service, mapper);
    }
}
