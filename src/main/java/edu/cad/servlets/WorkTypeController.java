package edu.cad.servlets;

import edu.cad.entities.WorkType;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WorkTypeController")
public class WorkTypeController extends AbstractEntityController<WorkType> {

    public WorkTypeController() {
        super(WorkType.class);
    }

    @Override
    protected WorkType getInstance(HttpServletRequest request) {
        WorkType instance = new WorkType();
        instance = initializeInstance(instance, request);
        setStringProperty(request, "denotation", instance::setDenotation);
        
        return instance;
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) 
            throws IOException {
        
        super.getDropDownList(WorkType::getDenotation, false, response);
    }    
}
