package edu.cad.controllers;

import edu.cad.entities.SubjectType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/subject-types")
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
