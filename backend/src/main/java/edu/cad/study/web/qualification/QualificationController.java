package edu.cad.study.web.qualification;

import edu.cad.entities.Qualification;
import edu.cad.study.service.QualificationService;
import edu.cad.study.web.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/qualifications")
public class QualificationController extends ActionController<Qualification, Integer, QualificationDto> {
    public QualificationController(QualificationService service, QualificationMapper mapper) {
        super(service, mapper);
    }
}
