package edu.cad.utils.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import edu.cad.entities.interfaces.IDatabaseEntity;

public abstract class AbstractEntitySerializer<T extends IDatabaseEntity> 
        implements JsonSerializer<T>{
    
    protected void addProperty(JsonElement jsonElement, String property, 
            IDatabaseEntity object, boolean canBeEmpty) {
        
        if(object != null){
            jsonElement.getAsJsonObject().addProperty(property, object.getId());
        } else if (canBeEmpty) {
            jsonElement.getAsJsonObject().addProperty(property, 0);
        }
    }
}
