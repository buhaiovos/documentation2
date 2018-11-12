package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.entities.Department;
import edu.cad.entities.Section;
import edu.cad.entities.SubjectHeader;
import edu.cad.entities.SubjectType;
import edu.cad.utils.gson.SubjectDictionarySerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/subject-headers")
public class SubjectHeaderController extends AbstractEntityController<SubjectHeader> {

    public SubjectHeaderController() {
        super(SubjectHeader.class);
    }

    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder()
                .registerTypeAdapter(SubjectHeader.class, new SubjectDictionarySerializer());
    }

    @Override
    protected SubjectHeader getInstance(HttpServletRequest request) {
        SubjectHeader subjectHeader = new SubjectHeader();
        subjectHeader = initializeInstance(subjectHeader, request);


        setStringProperty(request, "denotation", subjectHeader::setDenotation);
        setObjectProperty(request, "super_subject", subjectHeader::setSuperSubject,
                SubjectHeader.class);
        setObjectProperty(request, "department", subjectHeader::setDepartment,
                Department.class);
        setObjectProperty(request, "curriculum_section", subjectHeader::setCurriculumSection, Section.class);
        setObjectProperty(request, "workplan_section", subjectHeader::setWorkingPlanSection, Section.class);
        setObjectProperty(request, "type", subjectHeader::setType, SubjectType.class);

        return subjectHeader;
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(SubjectHeader::getDenotation, true, response);
    }

    @Override
    protected List<SubjectHeader> getList() {
        List<SubjectHeader> subjects = dao.getAll();
        Collections.sort(subjects);

        return subjects;
    }

}
