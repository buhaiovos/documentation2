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
        var practice = new Practice();
        practice = initializeInstance(practice, request);
        setIntProperty(request, "semester", practice::setSemester);
        setIntProperty(request, "weeks", practice::setWeeks);
        setStringProperty(request, "denotation", practice::setDenotationFromString);
        setDateProperty(request, "start", practice::setStart);
        setDateProperty(request, "finish", practice::setFinish);

        return practice;
    }
    
    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().setDateFormat("dd.MM.yyyy");
    }
    
}
