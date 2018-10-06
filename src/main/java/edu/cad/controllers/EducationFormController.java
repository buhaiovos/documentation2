package edu.cad.controllers;

import edu.cad.entities.EducationForm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/form-of-education")
public class EducationFormController extends AbstractEntityController<EducationForm>{

    public EducationFormController() {
        super(EducationForm.class);
    }
    
    @Override
    protected EducationForm getInstance(HttpServletRequest request) {
        EducationForm educationForm = new EducationForm();       
        educationForm = initializeInstance(educationForm, request);
        
        setStringProperty(request, "denotation", educationForm::setDenotation);
        
        return educationForm;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(EducationForm::getDenotation, false, response);
    }
    
}
