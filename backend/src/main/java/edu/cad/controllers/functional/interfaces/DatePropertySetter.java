package edu.cad.controllers.functional.interfaces;

import java.time.LocalDate;

@FunctionalInterface
public interface DatePropertySetter {
    void setProperty(LocalDate date);
}
