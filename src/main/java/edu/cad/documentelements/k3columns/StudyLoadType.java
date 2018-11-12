/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cad.documentelements.k3columns;

import static java.lang.String.format;

/**
 *
 * @author Олександр
 */
public enum StudyLoadType {
    LECTURES("k3(SL)lectures"),
    PRACTICES("k3(SL)practice"),
    LABS("k3(SL)lab"),
    INDIVIDUALS("k3(SL)individuals"),
    EXAMS("k3(SL)exam"),
    CREDITS("k3(SL)credit"),
    CONTROL_WORKS("k3(SL)contrwork"),
    COURSE_PROJECTS("k3(SL)courseproj"),
    COURSEWORKS("k3(SL)coursework"),
    RGRS("k3(SL)rgr"),
    DKR("k3(SL)dkr"),
    REFERATS("k3(SL)referat"),
    CONSULTATIONS("k3(SL)consult");
    
    private final String token;

    StudyLoadType(String token) {
        this.token = token;
    }
    
    public static StudyLoadType getByToken(String token) {
        StudyLoadType[] values = StudyLoadType.values();
        for (StudyLoadType value : values) {
            if (value.token.equals(token))
                return value;
        }
        throw new IllegalArgumentException(
                format("Given token: %s does not correspond to any of supported study load types.", token)
        );
    }
    
    public String getToken() {
        return token;
    }
    
}
