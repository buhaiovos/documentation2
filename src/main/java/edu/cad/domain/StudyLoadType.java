/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cad.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.lang.String.format;

/**
 * @author Олександр
 */
@AllArgsConstructor
@Getter
public enum StudyLoadType {
    LECTURES("lectures", "k3(SL)lectures"),
    PRACTICES("practices", "k3(SL)practice"),
    LABS("labs", "k3(SL)lab"),
    INDIVIDUALS("individuals", "k3(SL)individuals"),
    EXAMS("exams", "k3(SL)exam"),
    CREDITS("credits", "k3(SL)credit"),
    CONTROL_WORKS("contr_works", "k3(SL)contrwork"),
    COURSE_PROJECTS("course_projs", "k3(SL)courseproj"),
    COURSE_WORKS("course_works", "k3(SL)coursework"),
    RGRS("rgr", "k3(SL)rgr"),
    DKR("dkr", "k3(SL)dkr"),
    REFERATS("referats", "k3(SL)referat"),
    CONSULTATIONS("consult", "k3(SL)consult");

    @JsonValue
    private final String id;

    private final String k3Token;

    public static StudyLoadType getByToken(String token) {
        StudyLoadType[] values = StudyLoadType.values();
        for (StudyLoadType value : values) {
            if (value.k3Token.equals(token))
                return value;
        }
        throw new IllegalArgumentException(
                format("Given token: %s does not correspond to any of supported study load types.", token)
        );
    }
}
