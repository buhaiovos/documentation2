package edu.cad.study.diplomapreparation;

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
    int id;
    float norm;
    int workTypeId;
    int departmentId;
    int workingPlanId;
}
