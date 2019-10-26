package edu.cad.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import edu.cad.entities.Control;

import java.lang.reflect.Type;

public class ControlSerializer extends AbstractEntitySerializer<Control>{

    @Override
    public JsonElement serialize(Control instance, Type type, JsonSerializationContext jsc) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonElement jsonElement = gson.toJsonTree(instance);

        addProperty(jsonElement, "subject", instance.getSubjectInfo(), false);
        addProperty(jsonElement, "type", instance.getType(), false);
       
        return jsonElement.getAsJsonObject();
    }
    
}
