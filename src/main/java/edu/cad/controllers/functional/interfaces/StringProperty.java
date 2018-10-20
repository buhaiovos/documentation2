package edu.cad.controllers.functional.interfaces;

import edu.cad.entities.interfaces.IDatabaseEntity;

@FunctionalInterface
public interface StringProperty<T extends IDatabaseEntity>{
    String getValue(T instance);
}
