package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDao;
import edu.cad.entities.Department;
import edu.cad.entities.Specialization;
import edu.cad.utils.gson.SpecializationSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/specialization")
public class SpecializationController extends AbstractEntityController<Specialization> {

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
        return super.createGsonBuilder().registerTypeAdapter(Specialization.class, new SpecializationSerializer());
    }

    @Override
    protected void getDependencyList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            list.clear();
            list.addAll(new HibernateDao<>(Department.class).get(id).getSpecializations());

            putOk();
            content.put("Records", list);
            writeResponse(response);
        }
    }
}
