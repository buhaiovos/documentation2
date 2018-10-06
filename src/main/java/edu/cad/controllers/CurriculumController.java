package edu.cad.controllers;

import edu.cad.entities.Curriculum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractEntityController<Curriculum>{

    public CurriculumController() {
        super(Curriculum.class);
    }

    @Override
    protected Curriculum getInstance(HttpServletRequest request) {
        Curriculum curriculum = new Curriculum();
        curriculum = initializeInstance(curriculum, request);
        
        setStringProperty(request, "denotation", curriculum::setDenotation);
        
        return curriculum;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Curriculum::getDenotation, false, response);
    }
    
}
