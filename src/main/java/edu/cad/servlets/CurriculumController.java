package edu.cad.servlets;

import edu.cad.entities.Curriculum;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CurriculumController")
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
