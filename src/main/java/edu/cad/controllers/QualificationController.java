package edu.cad.controllers;

import edu.cad.entities.Qualification;
import edu.cad.study.persistence.QualificationRepository;
import edu.cad.utils.gson.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController("legacy-qualification-controller")
@RequestMapping("/qualification")
public class QualificationController extends AbstractEntityController<Qualification> {
    @Autowired
    private QualificationRepository repo;

    public QualificationController() {
        super(Qualification.class);
    }

    @Override
    protected Qualification getInstance(HttpServletRequest request) {
        Qualification qualification = new Qualification();
        qualification = initializeInstance(qualification, request);

        setStringProperty(request, "denotation", qualification::setDenotation);

        return qualification;
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        List<Option> options = new ArrayList<>();

        options.add(new Option("-", 0));

        for (Qualification instance : repo.findAll()) {
            options.add(new Option(instance.getDenotation(), instance.getId()));
        }

        putOk();
        content.put("Options", options);
        writeResponse(response);
    }
}
