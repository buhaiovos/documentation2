package edu.cad.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDAO;
import edu.cad.daos.IDAO;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.servlets.interfaces.*;
import edu.cad.utils.gson.HibernateProxyTypeAdapter;
import edu.cad.utils.gson.Option;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public abstract class AbstractEntityController<T extends IDatabaseEntity> {

    private String action;
    private final Class<T> typeParameterClass;

    IDAO<T> dao;
    Gson gson;
    Map<String, Object> content;
    List<T> list;

    public AbstractEntityController(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        dao = new HibernateDAO<>(typeParameterClass);
        list = new ArrayList<>();
    }

    @PostMapping
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        createGson();
        dao = new HibernateDAO<>(typeParameterClass);
        action = request.getParameter("action");
        content = new HashMap<>();
        setResponseSettings(response);
        processAction(request, response);
    }

    protected abstract void getDropDownList(HttpServletResponse response) throws IOException;

    protected abstract T getInstance(HttpServletRequest request);

    private void processAction(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {

        if (action != null) {
            try {
                switch (action) {
                    case "list":
                        processListAction(response);
                        break;
                    case "create":
                    case "update":
                        processCreateUpdateAction(request, response);
                        break;
                    case "delete":
                        processDeleteAction(request, response);
                        break;
                    case "dependencylist":
                        getDependencyList(request, response);
                        break;
                    case "dropdownlist":
                        getDropDownList(response);
                        break;
                }
            } catch (IOException | NumberFormatException ex) {
                content.put("Result", "ERROR");
                content.put("Message", ex.getMessage());
                writeResponse(response);
            }
        }
    }

    protected GsonBuilder createGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setPrettyPrinting();

        return builder;
    }

    void writeResponse(HttpServletResponse response) throws IOException {
        String jsonArray = gson.toJson(content);

        System.out.println(jsonArray);

        response.getWriter().print(jsonArray);
    }

    void putOk() {
        content.put("Result", "OK");
    }

    protected void setStringProperty(HttpServletRequest request,
                                     String requestParamString, StringPropertySetter propSetter) {

        String param = request.getParameter(requestParamString);
        if (param != null) {
            propSetter.setProperty(param);
        }
    }

    void setIntProperty(HttpServletRequest request,
                        String requestParamString, IntPropertySetter propSetter) {

        String numString = request.getParameter(requestParamString);
        if (numString != null) {
            int value = Integer.parseInt(numString.trim());
            propSetter.setProperty(value);
        }
    }

    void setBooleanProperty(HttpServletRequest request,
                            String requestParamString, BooleanPropertySetter propSetter) {

        String booleanValueString = request.getParameter(requestParamString);
        if (booleanValueString != null) {
            boolean value = Boolean.parseBoolean(booleanValueString);
            propSetter.setProperty(value);
        }
    }

    void setFloatProperty(HttpServletRequest request,
                          String requestParamString, FloatPropertySetter propSetter) {

        String numString = request.getParameter(requestParamString);
        if (numString != null) {
            float value = Float.parseFloat(numString);
            propSetter.setProperty(value);
        }
    }

    void setDateProperty(HttpServletRequest request,
                         String requestParamString, DatePropertySetter propSetter) {

        String dateString = request.getParameter(requestParamString);
        if (dateString != null) {
            Date date = gson.fromJson(dateString, Date.class);
            if (date != null) {
                propSetter.setProperty(date);
            }
        }
    }

    protected <E extends IDatabaseEntity>
    void setObjectProperty(HttpServletRequest request, String requestParamString,
                           ObjectPropertySetter<E> propSetter, Class<E> propertyObjClass) {

        String entityIdStr = request.getParameter(requestParamString);
        if (entityIdStr != null) {
            int id = Integer.parseInt(entityIdStr.trim());
            propSetter.setProperty(new HibernateDAO<>(propertyObjClass).get(id));
        }
    }

    protected T initializeInstance(T instance, HttpServletRequest request) {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));

            T found = dao.get(id);

            if (found != null)
                return found;

            instance.setId(id);
        }

        return instance;
    }

    protected void getDropDownList(StringProperty<T> property, boolean addNull,
                                   HttpServletResponse response) throws IOException {
        List<Option> options = new ArrayList();

        if (addNull) {
            options.add(new Option("-", 0));
        }

        for (T instance : getList()) {
            options.add(new Option(property.getValue(instance), instance.getId()));
        }

        putOk();
        content.put("Options", options);
        writeResponse(response);
    }

    protected void getDependencyList(HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        processListAction(response);
    }

    protected List<T> getList() {
        return dao.getAll();
    }

    private void setResponseSettings(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
    }

    private void createGson() {
        gson = createGsonBuilder().create();
    }

    private void processListAction(HttpServletResponse response)
            throws IOException {
        list = getList();

        putOk();
        content.put("Records", list);
        writeResponse(response);
    }

    private void processCreateUpdateAction(HttpServletRequest request,
                                           HttpServletResponse response) throws IOException {
        T instance = getInstance(request);
        if (action.equals("create")) {
            dao.create(instance);
        } else if (action.equals("update")) {
            dao.update(instance);
        }

        putOk();
        content.put("Record", instance);
        writeResponse(response);
    }

    private void processDeleteAction(HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {

        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);

            putOk();
            writeResponse(response);
        }
    }

}