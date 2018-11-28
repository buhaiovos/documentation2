package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.entities.EducationForm;
import edu.cad.entities.Staff;
import edu.cad.utils.gson.StaffSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/staff")
public class StaffController extends AbstractEntityController<Staff> {

    public StaffController() {
        super(Staff.class);
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Staff::toString, true, response);
    }

    @Override
    protected Staff getInstance(HttpServletRequest request) {
        var employee = new Staff();
        employee = initializeInstance(employee, request);
        setStringProperty(request, "firstName", employee::setFirstName);
        setStringProperty(request, "middleName", employee::setMiddleName);
        setStringProperty(request, "lastName", employee::setLastName);
        setStringProperty(request, "type", employee::setTypeFromString);
        setStringProperty(request, "sourceOfFinancing", employee::setSourceOfFinancingFromString);
        setStringProperty(request, "degree", employee::setDegree);
        setStringProperty(request, "position", employee::setPosition);
        setDoubleProperty(request, "rate", employee::setRate);
        setObjectProperty(request, "educationForm", employee::setEducationForm, EducationForm.class);

        return employee;
    }

    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(Staff.class,
                new StaffSerializer());
    }
}
