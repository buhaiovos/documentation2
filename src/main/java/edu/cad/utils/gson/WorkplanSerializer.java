package edu.cad.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import edu.cad.entities.Workplan;
import java.lang.reflect.Type;

public class WorkplanSerializer extends AbstractEntitySerializer<Workplan>{

    @Override
    public JsonElement serialize(Workplan instance, Type type, JsonSerializationContext jsc) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonElement jsonElement = gson.toJsonTree(instance);
        
        addProperty(jsonElement, "practice", instance.getPractice(), true);
        addProperty(jsonElement, "stateCertification", instance.getStateCertification(), true);
        addProperty(jsonElement, "curriculum", instance.getCurriculum(), true);
       
        return jsonElement.getAsJsonObject();
    }
    
}
