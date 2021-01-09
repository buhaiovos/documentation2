package edu.cad.study.academicgroup;

import edu.cad.study.DropdownOption;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AcademicGroupDto {
    private int id;
    private String cipher;
    private int budgetaryStudents;
    private int contractStudents;
    private int startYear;
    private DropdownOption specialization;
    private DropdownOption qualification;
    private DropdownOption educationForm;
    private DropdownOption workingPlan;
}
