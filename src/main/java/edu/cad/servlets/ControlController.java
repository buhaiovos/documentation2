package edu.cad.servlets;

import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDAO;
import edu.cad.entities.Control;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.Subject;
import edu.cad.utils.gson.ControlSerializer;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ControlController")
public class ControlController extends AbstractEntityController<Control>{

    public ControlController() {
        super(Control.class);
    }

    @Override
    protected Control getInstance(HttpServletRequest request) {
        Control control = new Control();
        control = initializeInstance(control, request);
        
        setIntProperty(request, "semester", control::setSemester);
        setObjectProperty(request, "subject", control::setSubject, Subject.class);
        setObjectProperty(request, "type", control::setType, ControlDictionary.class);
        
        return control;
    }
    
    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(Control.class, 
                new ControlSerializer());
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Control::toString, false, response);
    }
    
    @Override
    protected void getDependencyList(HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
        
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));  
            list.clear();
            list.addAll(new HibernateDAO<>(Subject.class).get(id).getControls());
            
            putOk();
            content.put("Records", list);
            writeResponse(response);
        }
    }
}
