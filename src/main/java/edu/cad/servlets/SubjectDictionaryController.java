package edu.cad.servlets;

import com.google.gson.GsonBuilder;
import edu.cad.entities.Department;
import edu.cad.entities.Section;
import edu.cad.entities.SubjectDictionary;
import edu.cad.entities.SubjectType;
import edu.cad.utils.gson.SubjectDictionarySerializer;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SubjectDictionaryController")
public class SubjectDictionaryController extends AbstractEntityController<SubjectDictionary>{

    public SubjectDictionaryController() {
        super(SubjectDictionary.class);
    }
    
    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(SubjectDictionary.class, 
                new SubjectDictionarySerializer());
    }

    @Override
    protected SubjectDictionary getInstance(HttpServletRequest request) {
        SubjectDictionary subjectDictionary = new SubjectDictionary();
        subjectDictionary = initializeInstance(subjectDictionary, request);
        
        
        setStringProperty(request, "denotation", subjectDictionary::setDenotation);
        setObjectProperty(request, "superSubject", subjectDictionary::setSuperSubject, 
                SubjectDictionary.class);
        setObjectProperty(request, "department", subjectDictionary::setDepartment, 
                Department.class);
        setObjectProperty(request, "curriculumSection", subjectDictionary::setCurriculumSection, Section.class);
        setObjectProperty(request, "workplanSection", subjectDictionary::setWorkplanSection, Section.class);
        setObjectProperty(request, "type", subjectDictionary::setType, SubjectType.class);
        
        return subjectDictionary;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(SubjectDictionary::getDenotation, true, response);
    }
    
    @Override
    protected List<SubjectDictionary> getList(){
        List<SubjectDictionary> subjects = dao.getAll();
        Collections.sort(subjects);
        
        return subjects;
    }
    
}
