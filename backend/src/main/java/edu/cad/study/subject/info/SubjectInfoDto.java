package edu.cad.study.subject.info;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import edu.cad.study.control.ControlDto;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record SubjectInfoDto(
        int id,
        int semester,
        int semestersDuration,
        int subjectHeaderId,
        double lectures,
        double actualLectures,
        double labs,
        double actualLabs,
        double practices,
        double actualPractices,
        double ects,
        ControlDto[] controls
) {}
