package edu.cad.study.subject.info;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record SubjectInfoDto(
        int id,
        int semester,
        int semestersDuration,
        double lectures,
        double actualLectures,
        double labs,
        double actualLabs,
        double practices,
        double actualPractices,
        double ects,
        int subjectHeaderId,
        int[]controlsIds
) {}
