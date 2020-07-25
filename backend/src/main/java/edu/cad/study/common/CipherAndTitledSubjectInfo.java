package edu.cad.study.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record CipherAndTitledSubjectInfo(
        String cipher,
        int id,
        String denotation,
        int semester,
        double ects,
        double lectures,
        double practices,
        double labs
) {}
