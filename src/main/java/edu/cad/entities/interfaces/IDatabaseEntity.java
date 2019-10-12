package edu.cad.entities.interfaces;

public interface IDatabaseEntity<T> {
    T getId();

    void setIdentifier(T id);
}
