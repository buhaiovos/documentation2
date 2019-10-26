package edu.cad.study.web.educationform;

import edu.cad.entities.EducationForm;
import edu.cad.study.service.EducationFormService;
import edu.cad.study.web.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v2/education-forms")
public class EducationFormController extends ActionController<EducationForm, Integer, EducationFormDto> {
    public EducationFormController(EducationFormService service, EducationFormMapper mapper) {
        super(service, mapper);
    }

    @Override
    public List<EducationFormDto> getDependent(HttpServletRequest request) {
        throw new UnsupportedOperationException("Unsupported");
    }
}
