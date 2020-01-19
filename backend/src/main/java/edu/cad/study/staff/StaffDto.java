package edu.cad.study.staff;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffDto {
    int id;
    int educationFormId;
    String firstName;
    String lastName;
    String middleName;
    String type;
    String degree;
    String position;
    double rate;
    String sourceOfFinancing;
}
