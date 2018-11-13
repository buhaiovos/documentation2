package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDao;
import edu.cad.entities.SubjectHeader;
import edu.cad.entities.SubjectInfo;
import edu.cad.utils.gson.SubjectSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/subject-info")
public class SubjectInfoController extends AbstractEntityController<SubjectInfo> {

    public SubjectInfoController() {
        super(SubjectInfo.class);
    }

    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(SubjectInfo.class,
                new SubjectSerializer());
    }

    @Override
    protected SubjectInfo getInstance(HttpServletRequest request) {
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo = initializeInstance(subjectInfo, request);

        setIntProperty(request, "semester", subjectInfo::setSemester);
        setIntProperty(request, "semesters_duration", subjectInfo::setSemestersDuration);

        setDoubleProperty(request, "ects", subjectInfo::setEcts);
        setDoubleProperty(request, "lectures", subjectInfo::setLectures);
        setDoubleProperty(request, "labs", subjectInfo::setLabs);
        setDoubleProperty(request, "practices", subjectInfo::setPractices);
        setDoubleProperty(request, "actual_lectures", subjectInfo::setActualLectures);
        setDoubleProperty(request, "actual_practices", subjectInfo::setActualPractices);
        setDoubleProperty(request, "actual_labs", subjectInfo::setActualLabs);

        setObjectProperty(request, "subject_header", subjectInfo::setSubjectHeader, SubjectHeader.class);

        return subjectInfo;
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(SubjectInfo::toString, false, response);
    }

    @Override
    protected void getDependencyList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            list.clear();
            list.addAll(new HibernateDao<>(SubjectHeader.class).get(id).getSubjectInfo());

            putOk();
            content.put("Records", list);
            writeResponse(response);
        }
    }

}
