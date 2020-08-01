package edu.cad.study.subject.info;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import edu.cad.study.control.ControlDto;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record RichSubjectInfoDto(
        int id,
        String denotation,
        int semester,
        int semestersDuration,
        double lectures,
        double actualLectures,
        double labs,
        double actualLabs,
        double practices,
        double actualPractices,
        double ects,
        ControlDto[] controls
) {}
