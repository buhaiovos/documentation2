package edu.cad.study.specialization;

import edu.cad.study.DropdownOption;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecializationDto {
    int id;
    String denotation;
    DropdownOption department;
}
