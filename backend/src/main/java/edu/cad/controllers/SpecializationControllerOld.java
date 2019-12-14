package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDao;
import edu.cad.entities.Department;
import edu.cad.entities.Specialization;
import edu.cad.study.specialization.SpecializationRepository;
import edu.cad.utils.gson.Option;
import edu.cad.utils.gson.SpecializationSerializer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/specialization")
@Setter
public class SpecializationControllerOld extends AbstractEntityController<Specialization> {
    @Autowired
    private SpecializationRepository repo;

    public SpecializationControllerOld() {
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
        List<Option> options = new ArrayList<>();

        options.add(new Option("-", 0));

        for (Specialization instance : repo.findAll()) {
            options.add(new Option(instance.getDenotation(), instance.getId()));
        }

        putOk();
        content.put("Options", options);
        writeResponse(response);
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
