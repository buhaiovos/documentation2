package edu.cad.study.subject.type;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SubjectTypeDto {
    int id;
    String denotation;
}
