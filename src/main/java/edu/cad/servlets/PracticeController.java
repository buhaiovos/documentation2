package edu.cad.servlets;

import com.google.gson.GsonBuilder;
import edu.cad.entities.Practice;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PracticeController")
public class PracticeController extends AbstractEntityController<Practice> {

    public PracticeController() {
        super(Practice.class);
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Practice::toString, true, response);
    }

    @Override
    protected Practice getInstance(HttpServletRequest request) {
        Practice instance = new Practice();
        instance = initializeInstance(instance, request);
        setIntProperty(request, "semester", instance::setSemester);
        setIntProperty(request, "weeks", instance::setWeeks);
        setStringProperty(request, "denotation", instance::setDenotation);
        setDateProperty(request, "start", instance::setStart);
        setDateProperty(request, "finish", instance::setFinish);
        
        return instance;
    }
    
    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().setDateFormat("dd.MM.yyyy");
    }
    
}
