package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDAO;
import edu.cad.entities.Subject;
import edu.cad.entities.SubjectDictionary;
import edu.cad.utils.gson.SubjectSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/subjects")
public class SubjectController extends AbstractEntityController<Subject> {

    public SubjectController() {
        super(Subject.class);
    }

    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(Subject.class,
                new SubjectSerializer());
    }

    @Override
    protected Subject getInstance(HttpServletRequest request) {
        Subject subject = new Subject();
        subject = initializeInstance(subject, request);

        setIntProperty(request, "semester", subject::setSemester);
        setIntProperty(request, "semestersDuration", subject::setSemestersDuration);
        setFloatProperty(request, "ects", subject::setEcts);
        setIntProperty(request, "lections", subject::setLections);
        setIntProperty(request, "labs", subject::setLabs);
        setIntProperty(request, "practices", subject::setPractices);
        setObjectProperty(request, "subject", subject::setSubject, SubjectDictionary.class);

        return subject;
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Subject::toString, false, response);
    }

    @Override
    protected void getDependencyList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            list.clear();
            list.addAll(new HibernateDAO<>(SubjectDictionary.class).get(id).getAcademicSubjects());

            putOk();
            content.put("Records", list);
            writeResponse(response);
        }
    }

}
