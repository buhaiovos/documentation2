package edu.cad.servlets;

import edu.cad.entities.Qualification;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/QualificationController")
public class QualificationController 
        extends AbstractEntityController<Qualification> {

    public QualificationController() {
        super(Qualification.class);
    }

    @Override
    protected Qualification getInstance(HttpServletRequest request) {
        Qualification qualification = new Qualification();       
        qualification = initializeInstance(qualification, request);
        
        setStringProperty(request, "denotation", qualification::setDenotation);
        
        return qualification;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Qualification::getDenotation, false, response);
    }
}
