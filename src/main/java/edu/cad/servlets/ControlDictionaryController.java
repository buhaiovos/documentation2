package edu.cad.servlets;

import edu.cad.entities.ControlDictionary;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ControlDictionaryController")
public class ControlDictionaryController 
        extends AbstractEntityController<ControlDictionary> {

    public ControlDictionaryController() {
        super(ControlDictionary.class);
    }

    @Override
    protected ControlDictionary getInstance(HttpServletRequest request) {
        ControlDictionary instance = new ControlDictionary();
        instance = initializeInstance(instance, request);
        setStringProperty(request, "denotation", instance::setDenotation);
        
        return instance;
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(ControlDictionary::getDenotation, false, response);
    }
    
}
