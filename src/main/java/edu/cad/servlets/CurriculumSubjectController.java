package edu.cad.servlets;

import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDAO;
import edu.cad.entities.Curriculum;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.Subject;
import edu.cad.utils.gson.CurriculumSubjectSerializer;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CurriculumSubjectController")
public class CurriculumSubjectController extends AbstractEntityController<CurriculumSubject>{

    public CurriculumSubjectController() {
        super(CurriculumSubject.class);
    }

    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(CurriculumSubject.class, 
                new CurriculumSubjectSerializer());
    }

    @Override
    protected CurriculumSubject getInstance(HttpServletRequest request) {
        CurriculumSubject curriculumSubject = new CurriculumSubject();
        curriculumSubject = initializeInstance(curriculumSubject, request);
        
        setStringProperty(request, "cipher", curriculumSubject::setCipher);
        setObjectProperty(request, "curriculum", curriculumSubject::setCurriculum, Curriculum.class);
        setObjectProperty(request, "subject", curriculumSubject::setSubject, Subject.class);
        
        return curriculumSubject;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(CurriculumSubject::toString, false, response);
    }
    
    @Override
    protected void getDependencyList(HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
        
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));  
            list.clear();
            list.addAll(new HibernateDAO<>(Curriculum.class).get(id).getCurriculumSubjects());
            Collections.sort(list);
            
            putOk();
            content.put("Records", list);
            writeResponse(response);
        }
    }
    
}
