package edu.cad.servlets;

import edu.cad.entities.EducationForm;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EducationFormController")
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
