package edu.cad.study.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static java.util.Objects.requireNonNull;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record CipherAndTitledSubjectInfo(
        String cipher,
        Integer id,
        String denotation,
        Integer semester,
        Double ects,
        Double lectures,
        Double practices,
        Double labs
) {
    public CipherAndTitledSubjectInfo {
        requireNonNull(cipher);
        requireNonNull(id);
    }
}
