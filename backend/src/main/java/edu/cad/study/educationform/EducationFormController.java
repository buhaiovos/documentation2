package edu.cad.study.educationform;

import edu.cad.entities.EducationForm;
import edu.cad.study.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/education-forms")
public class EducationFormController extends ActionController<EducationForm, Integer, EducationFormDto> {
    public EducationFormController(EducationFormService service, EducationFormMapper mapper) {
        super(service, mapper);
    }
}
