package edu.cad.servlets.interfaces;

import java.util.Date;

@FunctionalInterface
public interface DatePropertySetter {
    void setProperty(Date date);
}
