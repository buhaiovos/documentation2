package edu.cad.servlets;

import com.google.gson.GsonBuilder;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.EducationForm;
import edu.cad.entities.Qualification;
import edu.cad.entities.Specialization;
import edu.cad.entities.Workplan;
import edu.cad.utils.gson.AcademicGroupSerializer;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AcademicGroupController")
public class AcademicGroupController extends AbstractEntityController<AcademicGroup>{

    public AcademicGroupController() {
        super(AcademicGroup.class);
    }

    @Override
    protected AcademicGroup getInstance(HttpServletRequest request) {       
        AcademicGroup group = new AcademicGroup();
        group = initializeInstance(group, request);
        
        setStringProperty(request, "cipher", group::setCipher);    
        setIntProperty(request, "budgetaryStudents", group::setBudgetaryStudents);
        setIntProperty(request, "contractStudents", group::setContractStudents);
        setIntProperty(request, "startYear", group::setStartYear);
        
        setObjectProperty(request, "specialization", group::setSpecialization, 
                Specialization.class);
        
        setObjectProperty(request, "qualification", group::setQualification, 
                Qualification.class);
        
        setObjectProperty(request, "educationForm", group::setEducationForm, 
                EducationForm.class);
        
        setObjectProperty(request, "workplan", group::setWorkplan, 
                Workplan.class);
        return group;
    }
    
    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(AcademicGroup.class, 
                new AcademicGroupSerializer());
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(AcademicGroup::getCipher, false, response);
    }    
}
