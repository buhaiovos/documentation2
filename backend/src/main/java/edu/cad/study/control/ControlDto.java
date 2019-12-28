package edu.cad.study.control;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ControlDto {
    private int id;
    private int semester;
    private int typeId;
    private int subjectId;
}
