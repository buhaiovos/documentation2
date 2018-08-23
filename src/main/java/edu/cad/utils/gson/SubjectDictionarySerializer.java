package edu.cad.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import edu.cad.entities.SubjectDictionary;
import java.lang.reflect.Type;

public class SubjectDictionarySerializer extends AbstractEntitySerializer<SubjectDictionary>{

    @Override
    public JsonElement serialize(SubjectDictionary instance, Type type, JsonSerializationContext jsc) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonElement jsonElement = gson.toJsonTree(instance);
        
        addProperty(jsonElement, "superSubject", instance.getSuperSubject(), true);
        addProperty(jsonElement, "department", instance.getDepartment(), true);
        addProperty(jsonElement, "curriculumSection", instance.getCurriculumSection(), true);
        addProperty(jsonElement, "workplanSection", instance.getWorkplanSection(), true);
        addProperty(jsonElement, "type", instance.getType(), false);
       
        return jsonElement.getAsJsonObject();
    }
    
}
