package edu.cad.controllers.functional.interfaces;

@FunctionalInterface
public interface ObjectPropertySetter<T> {
    void setProperty(T object);
}
