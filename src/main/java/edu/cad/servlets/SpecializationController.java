package edu.cad.servlets;

import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDAO;
import edu.cad.entities.Department;
import edu.cad.entities.Specialization;
import edu.cad.utils.gson.SpecializationSerializer;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SpecializationController")
public class SpecializationController extends AbstractEntityController<Specialization>{

    public SpecializationController() {
        super(Specialization.class);
    }

    @Override
    protected Specialization getInstance(HttpServletRequest request) {
        Specialization specialization = new Specialization();
        specialization = initializeInstance(specialization, request);
        
        setStringProperty(request, "denotation", specialization::setDenotation);
        setObjectProperty(request, "department", specialization::setDepartment, Department.class);
        
        return specialization;
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Specialization::getDenotation, false, response);
    }
    
    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(Specialization.class, 
                new SpecializationSerializer());
    }
    
    @Override
    protected void getDependencyList(HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
        
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));  
            list.clear();
            list.addAll(new HibernateDAO<>(Department.class).get(id).getSpecializations());
            
            putOk();
            content.put("Records", list);
            writeResponse(response);
        }
    }
}
