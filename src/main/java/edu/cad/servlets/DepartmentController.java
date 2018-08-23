package edu.cad.servlets;

import edu.cad.entities.Department;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DepartmentController")
public class DepartmentController extends AbstractEntityController<Department>{

    public DepartmentController() {
        super(Department.class);
    }
    
    @Override
    protected Department getInstance(HttpServletRequest request) {
        Department department = new Department();       
        department = initializeInstance(department, request);
        
        setStringProperty(request, "denotation", department::setDenotation);

        return department;
    }
    
    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Department::getDenotation, true, response);
    }  
}
