package edu.cad.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import edu.cad.entities.Section;
import java.lang.reflect.Type;

public class SectionSerializer extends AbstractEntitySerializer<Section>{

    @Override
    public JsonElement serialize(Section instance, Type type, JsonSerializationContext jsc) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonElement jsonElement = gson.toJsonTree(instance);
        
        addProperty(jsonElement, "cycle", instance.getCycle(), false);
       
        return jsonElement.getAsJsonObject();
    }
    
}
