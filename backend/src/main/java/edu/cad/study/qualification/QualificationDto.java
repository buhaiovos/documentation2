package edu.cad.study.qualification;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QualificationDto {
    Integer id;
    String denotation;
}
