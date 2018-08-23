package edu.cad.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cad.daos.HibernateDAO;
import edu.cad.daos.IDAO;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.servlets.interfaces.BooleanPropertySetter;
import edu.cad.servlets.interfaces.DatePropertySetter;
import edu.cad.servlets.interfaces.FloatPropertySetter;
import edu.cad.servlets.interfaces.IntPropertySetter;
import edu.cad.servlets.interfaces.ObjectPropertySetter;
import edu.cad.servlets.interfaces.StringProperty;
import edu.cad.servlets.interfaces.StringPropertySetter;
import edu.cad.utils.gson.HibernateProxyTypeAdapter;
import edu.cad.utils.gson.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractEntityController<T extends IDatabaseEntity> 
        extends HttpServlet {

    protected IDAO<T> dao;
    protected Gson gson;   
    protected Map<String, Object> content;
    protected List<T> list;
    
    private String action;
    private final Class<T> typeParameterClass;

    public AbstractEntityController(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        dao = new HibernateDAO<>(typeParameterClass);
        list = new ArrayList<>();
    }

    protected abstract void getDropDownList(HttpServletResponse response) throws IOException;
    
    protected abstract T getInstance(HttpServletRequest request);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        createGson();
        dao = new HibernateDAO<>(typeParameterClass);
        action = request.getParameter("action");
        content = new HashMap<>();
        setResponseSettings(response);
        processAction(request, response);
    }

    protected GsonBuilder createGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setPrettyPrinting();
        
        return builder;
    }  

    protected void writeResponse(HttpServletResponse response) throws IOException {
        String jsonArray = gson.toJson(content);
        
        System.out.println(jsonArray);
        
        response.getWriter().print(jsonArray);
    }
    
    protected void putOk() {
        content.put("Result", "OK");
    }
    
    protected void setStringProperty(HttpServletRequest request, 
            String requestParamString, StringPropertySetter propSetter) {
        
        String param = request.getParameter(requestParamString);
        if (param != null) {
            propSetter.setProperty(param);
        }
    }
    
    protected void setIntProperty(HttpServletRequest request,
            String requestParamString, IntPropertySetter propSetter) {
        
        String numString = request.getParameter(requestParamString);
        if (numString != null) {
            int value = Integer.parseInt(numString.trim());
            propSetter.setProperty(value);
        }
    }
    
    protected void setBooleanProperty(HttpServletRequest request,
            String requestParamString, BooleanPropertySetter propSetter) {
        
        String booleanValueString = request.getParameter(requestParamString);
        if (booleanValueString != null) {
            boolean value = Boolean.parseBoolean(booleanValueString);
            propSetter.setProperty(value);
        }
    }
    
    protected void setFloatProperty(HttpServletRequest request,
            String requestParamString, FloatPropertySetter propSetter) {
        
        String numString = request.getParameter(requestParamString);
        if (numString != null) {
            float value = Float.parseFloat(numString);
            propSetter.setProperty(value);
        }
    }
    
    protected void setDateProperty(HttpServletRequest request,
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

    protected T initializeInstance(T instance, HttpServletRequest request){
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            
            T found = dao.get(id);
            
            if(found != null) 
                return found;
                
            instance.setId(id);      
        }
        
        return  instance;
    }

    protected void getDropDownList(StringProperty<T> property, boolean addNull,
            HttpServletResponse response) throws IOException {
        List<Option> options = new ArrayList();

        if(addNull){
            options.add(new Option("-", 0));
        }
        
        for(T instance : getList()){
            options.add(new Option(property.getValue(instance), instance.getId()));
        }
 
        putOk();
        content.put("Options", options);
        writeResponse(response);
    }
    
    protected void getDependencyList(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
        processListAction(response);
    }
    
    protected List<T> getList(){
        return dao.getAll();
    }
    
    private void setResponseSettings(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
    }
    
    private void createGson() {       
        gson = createGsonBuilder().create();
    }

    private void processAction(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        if (action != null) {
            try {
                switch(action) {
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
            }
            catch (IOException | NumberFormatException ex) {
                content.put("Result", "ERROR");
                content.put("Message", ex.getMessage());
                writeResponse(response);
            }
        }
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