package edu.cad.study.diplomapreparation;

import edu.cad.study.DropdownOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class DiplomaPreparationDto {
    Integer id;
    float norm;
    DropdownOption workType;
    DropdownOption department;
    DropdownOption workingPlan;
}
