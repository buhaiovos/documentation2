package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.entities.*;
import edu.cad.utils.gson.AcademicGroupSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/academic-group")
public class AcademicGroupController extends AbstractEntityController<AcademicGroup> {

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