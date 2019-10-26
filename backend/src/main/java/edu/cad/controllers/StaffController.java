package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.entities.EducationForm;
import edu.cad.entities.Staff;
import edu.cad.utils.gson.StaffSerializer;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

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


    @GetMapping("/all")
    public List<StaffDto> getAll() {
        return this.dao.getAll()
                .stream()
                .map(this::toDto)
                .collect(toList());
    }

    private StaffDto toDto(Staff staff) {
        return new StaffDto()
                .setId(staff.getId())
                .setLabel(staff.getFullName());

    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Accessors(chain = true)
    private static class StaffDto {
        private long id;
        private String label;
    }
}
