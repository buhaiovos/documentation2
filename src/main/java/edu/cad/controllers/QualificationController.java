package edu.cad.controllers;

import edu.cad.entities.Qualification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/qualification")
public class QualificationController 
        extends AbstractEntityController<Qualification> {

    public QualificationController() {
        super(Qualification.class);
    }

    @Override
    protected Qualification getInstance(HttpServletRequest request) {
        Qualification qualification = new Qualification();       
        qualification = initializeInstance(qualification, request);
        
        setStringProperty(request, "denotation", qualification::setDenotation);
        
        return qualification;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Qualification::getDenotation, false, response);
    }
}
