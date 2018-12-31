package edu.cad.domain;

import lombok.Getter;

import static edu.cad.domain.ObjectOfWork.*;

@Getter
public enum PracticeType {
    PRE_DIPLOMA("Переддипломна", PRE_DIPLOMA_PRACTICE),
    PEDAGOGICAL("Педагогічна", PEDAGOGICAL_PRACTICE),
    SCI_RESEARCH("Науково-дослідна", SCI_RESEARCH_PRACTICE);

    private final String denotation;
    private final ObjectOfWork objectOfWork;

    PracticeType(String denotation, ObjectOfWork objectOfWork) {
        this.denotation = denotation;
        this.objectOfWork = objectOfWork;
    }
}
