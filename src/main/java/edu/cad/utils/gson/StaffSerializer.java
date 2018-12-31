package edu.cad.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import edu.cad.entities.Staff;

import java.lang.reflect.Type;

public class StaffSerializer extends AbstractEntitySerializer<Staff> {

    @Override
    public JsonElement serialize(Staff instance, Type type, JsonSerializationContext jsc) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonElement jsonElement = gson.toJsonTree(instance);

        addProperty(jsonElement, "educationForm", instance.getEducationForm(), false);

        return jsonElement.getAsJsonObject();
    }
}
