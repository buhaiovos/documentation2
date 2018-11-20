package edu.cad.domain;

import lombok.Getter;

@Getter
public enum QualificationLevel {
    BACHELOR(1),
    MASTER(2),
    SPECIALIST(3), //just in case...
    PHD(4),
    PROF_MASTER(5),
    SCI_MASTER(6);

    private final int dbId;

    QualificationLevel(int dbId) {
        this.dbId = dbId;
    }
}
