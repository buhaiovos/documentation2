package edu.cad.study.diplomapreparation;

import edu.cad.entities.DiplomaPreparation;
import edu.cad.study.ActionController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/diploma-preparations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiplomaPreparationController extends ActionController<DiplomaPreparation, Integer, DiplomaPreparationDto> {
    public DiplomaPreparationController(DiplomaPreparationService service, DiplomaPreparationMapper mapper) {
        super(service, mapper);
    }
}
