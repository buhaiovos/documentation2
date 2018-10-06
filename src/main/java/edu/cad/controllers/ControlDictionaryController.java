package edu.cad.controllers;

import edu.cad.entities.ControlDictionary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/control-dictionary")
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
