package edu.cad.servlets.interfaces;

@FunctionalInterface
public interface ObjectPropertySetter<T> {
    void setProperty(T object);
}
