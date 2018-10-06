package edu.cad.controllers;

import edu.cad.entities.WorkType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/work-type")
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
