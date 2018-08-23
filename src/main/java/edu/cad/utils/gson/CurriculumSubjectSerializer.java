package edu.cad.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import edu.cad.entities.CurriculumSubject;
import java.lang.reflect.Type;

public class CurriculumSubjectSerializer extends AbstractEntitySerializer<CurriculumSubject>{

    @Override
    public JsonElement serialize(CurriculumSubject instance, Type type, JsonSerializationContext jsc) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonElement jsonElement = gson.toJsonTree(instance);
        
        addProperty(jsonElement, "curriculum", instance.getCurriculum(), false);
        addProperty(jsonElement, "subject", instance.getSubject(), false);
       
        return jsonElement.getAsJsonObject();
    }
    
}
