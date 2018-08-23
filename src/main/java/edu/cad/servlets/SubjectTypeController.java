package edu.cad.servlets;

import edu.cad.entities.SubjectType;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SubjectTypeController")
public class SubjectTypeController extends AbstractEntityController<SubjectType> {

    public SubjectTypeController() {
        super(SubjectType.class);
    }

    @Override
    protected SubjectType getInstance(HttpServletRequest request) {
        SubjectType instance = new SubjectType();
        instance = initializeInstance(instance, request);
        setStringProperty(request, "denotation", instance::setDenotation);
        
        return instance;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) 
            throws IOException {
        
        super.getDropDownList(SubjectType::getDenotation, false, response);
    }
    
}
