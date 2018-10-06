package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.entities.Curriculum;
import edu.cad.entities.Practice;
import edu.cad.entities.StateCertification;
import edu.cad.entities.Workplan;
import edu.cad.utils.gson.WorkplanSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/workplans")
public class WorkplanController extends AbstractEntityController<Workplan>{

    public WorkplanController() {
        super(Workplan.class);
    }
    
    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(Workplan.class, 
                new WorkplanSerializer());
    }


    @Override
    protected Workplan getInstance(HttpServletRequest request) {
        Workplan workplan = new Workplan();
        workplan = initializeInstance(workplan, request);
        
        setStringProperty(request, "denotation", workplan::setDenotation);
        setObjectProperty(request, "curriculum", workplan::setCurriculum, Curriculum.class);
        setObjectProperty(request, "practice", workplan::setPractice, Practice.class);
        setObjectProperty(request, "stateCertification", workplan::setStateCertification, StateCertification.class);
        
        return workplan;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Workplan::getDenotation, false, response);
    }
}
