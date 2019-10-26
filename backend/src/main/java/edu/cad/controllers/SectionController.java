package edu.cad.controllers;

import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDao;
import edu.cad.entities.Cycle;
import edu.cad.entities.Section;
import edu.cad.utils.gson.SectionSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/section")
public class SectionController extends AbstractEntityController<Section> {

    public SectionController() {
        super(Section.class);
    }

    @Override
    protected Section getInstance(HttpServletRequest request) {
        Section section = new Section();
        section = initializeInstance(section, request);

        setStringProperty(request, "denotation", section::setDenotation);
        setBooleanProperty(request, "optional", section::setOptional);
        setObjectProperty(request, "cycle", section::setCycle, Cycle.class);

        return section;
    }

    @Override
    protected void getDropDownList(HttpServletResponse response) throws IOException {
        super.getDropDownList(Section::getDenotation, true, response);
    }

    @Override
    protected GsonBuilder createGsonBuilder() {
        return super.createGsonBuilder().registerTypeAdapter(Section.class,
                new SectionSerializer());
    }

    @Override
    protected void getDependencyList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            list.clear();
            list.addAll(new HibernateDao<>(Cycle.class).get(id).getSections());

            putOk();
            content.put("Records", list);
            writeResponse(response);
        }
    }

}
