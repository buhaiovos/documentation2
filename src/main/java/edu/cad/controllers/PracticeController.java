package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.entities.Practice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/practice")
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
