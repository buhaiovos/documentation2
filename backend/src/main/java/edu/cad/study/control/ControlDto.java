package edu.cad.study.control;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ControlDto(
        Integer id,
        Integer semester,
        Integer typeId,
        String name,
        Integer subjectId
) {}
