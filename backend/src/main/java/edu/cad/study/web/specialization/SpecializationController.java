package edu.cad.study.web.specialization;

import edu.cad.entities.Specialization;
import edu.cad.study.service.SpecializationService;
import edu.cad.study.web.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/specializations")
public class SpecializationController extends ActionController<Specialization, Integer, SpecializationDto> {
    public SpecializationController(SpecializationService service, SpecializationMapper mapper) {
        super(service, mapper);
    }
}
