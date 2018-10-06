package edu.cad.controllers;

import edu.cad.entities.Department;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/department")
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
