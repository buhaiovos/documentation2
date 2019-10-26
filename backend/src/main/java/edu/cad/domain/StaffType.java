package edu.cad.domain;

import lombok.Getter;

@Getter
public enum StaffType {
    POSTGRADUATE(ObjectOfWork.POSTGRADUATES),
    TRAINEE(ObjectOfWork.APPLICANTS_AND_TRAINEES),
    DOCTORAL_STUDENT(ObjectOfWork.DOCTORAL_STUDENTS),
    PROFESSOR(null);

    private final ObjectOfWork objectOfWork;

    StaffType(ObjectOfWork objectOfWork) {
        this.objectOfWork = objectOfWork;
    }
}
