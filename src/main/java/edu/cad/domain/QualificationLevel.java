package edu.cad.domain;

import lombok.Getter;

@Getter
public enum QualificationLevel {
    BACHELOR(1, ObjectOfWork.BACHELORS),
    MASTER(2, null),
    SPECIALIST(3, null),
    PHD(4, ObjectOfWork.PHD),
    PROF_MASTER(3, ObjectOfWork.PRO_MASTERS),
    SCI_MASTER(2, ObjectOfWork.SCI_MASTERS);

    private final int dbId;
    private final ObjectOfWork objectOfWork;

    QualificationLevel(int dbId, ObjectOfWork objectOfWork) {
        this.dbId = dbId;
        this.objectOfWork = objectOfWork;
    }
}
