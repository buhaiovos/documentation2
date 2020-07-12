package edu.cad.study.qualification;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record QualificationDto(Integer id, String denotation) {}
