package edu.cad.servlets.interfaces;

import edu.cad.entities.interfaces.IDatabaseEntity;

@FunctionalInterface
public interface StringProperty<T extends IDatabaseEntity>{
    public String getValue(T instance);
}
