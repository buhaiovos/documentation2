package edu.cad.servlets;

import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDAO;
import edu.cad.entities.Department;
import edu.cad.entities.DiplomaPreparation;
import edu.cad.entities.WorkType;
import edu.cad.entities.Workplan;
import edu.cad.utils.gson.DiplomaPreparationSerializer;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DiplomaPreparationController")
public class DiplomaPreparationController extends AbstractEntityController<DiplomaPreparation>{

    public DiplomaPreparationController() {
        super(DiplomaPreparation.class);
    }
    
    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(DiplomaPreparation.class, 
                new DiplomaPreparationSerializer());
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(DiplomaPreparation::toString, true, response);
    }

    @Override
    protected DiplomaPreparation getInstance(HttpServletRequest request) {
        DiplomaPreparation diplomaPreparation = new DiplomaPreparation();
        diplomaPreparation = initializeInstance(diplomaPreparation, request);
        
        setFloatProperty(request, "norm", diplomaPreparation::setNorm);
        setObjectProperty(request, "workType", diplomaPreparation::setWorkType, WorkType.class);
        setObjectProperty(request, "workplan", diplomaPreparation::setWorkplan, Workplan.class);
        setObjectProperty(request, "department", diplomaPreparation::setDepartment, Department.class);
        
        return diplomaPreparation;
    }
    
    @Override
    protected void getDependencyList(HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
        
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));  
            list.clear();
            list.addAll(new HibernateDAO<>(Workplan.class).get(id).getDiplomaPreparations());
            
            putOk();
            content.put("Records", list);
            writeResponse(response);
        }
    }
}
