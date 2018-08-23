package edu.cad.servlets;

import edu.cad.entities.Cycle;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CycleController")
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
