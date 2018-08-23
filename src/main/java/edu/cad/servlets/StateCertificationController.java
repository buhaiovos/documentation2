package edu.cad.servlets;

import com.google.gson.GsonBuilder;
import edu.cad.entities.StateCertification;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StateCertificationController")
public class StateCertificationController extends 
        AbstractEntityController<StateCertification> {

    public StateCertificationController() {
        super(StateCertification.class);
    }

    @Override
    protected StateCertification getInstance(HttpServletRequest request) {
        StateCertification instance = new StateCertification();
        instance = initializeInstance(instance, request);
        setStringProperty(request, "form", instance::setForm);
        setIntProperty(request, "semester", instance::setSemester);
        setDateProperty(request, "start", instance::setStart);
        setDateProperty(request, "finish", instance::setFinish);
        
        return instance;
    }
    
    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().setDateFormat("dd.MM.yyyy");
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(StateCertification::toString, true, response);
    }    
    
}
