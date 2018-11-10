package edu.cad.controllers;

import edu.cad.entities.Cycle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/cycle")
public class CycleController extends AbstractEntityController<Cycle>{

    public CycleController() {
        super(Cycle.class);
    }

    @Override
    protected Cycle getInstance(HttpServletRequest request) {
        Cycle cycle = new Cycle();
        cycle = initializeInstance(cycle, request);

        setStringProperty(request, "denotation", cycle::setDenotation);

        return cycle;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Cycle::getDenotation, false, response);
    }
    
}
