package edu.cad.entities.interfaces;

import edu.cad.entities.SubjectInfo;

@FunctionalInterface
public interface SubjectProperty {
    double getValue(SubjectInfo subjectDetails);
}
