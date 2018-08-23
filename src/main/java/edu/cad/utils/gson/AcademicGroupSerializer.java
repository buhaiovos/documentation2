package edu.cad.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import edu.cad.entities.AcademicGroup;
import java.lang.reflect.Type;

public class AcademicGroupSerializer extends AbstractEntitySerializer<AcademicGroup>{

    @Override
    public JsonElement serialize(AcademicGroup instance, Type type, JsonSerializationContext jsc) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonElement jsonElement = gson.toJsonTree(instance);
        
        addProperty(jsonElement, "specialization", instance.getSpecialization(), false);
        addProperty(jsonElement, "qualification", instance.getQualification(), false);
        addProperty(jsonElement, "educationForm", instance.getEducationForm(), false);
        addProperty(jsonElement, "workplan", instance.getWorkplan(), false);
       
        return jsonElement.getAsJsonObject();
    }
    
    
    
}
