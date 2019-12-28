package edu.cad.study.subject.info;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectInfoDto {
    int id;
    int semester;
    int semestersDuration;
    double lectures;
    double actualLectures;
    double labs;
    double actualLabs;
    double practices;
    double actualPractices;
    double ects;
    int subjectHeaderId;
    int[] controlsIds;
}
