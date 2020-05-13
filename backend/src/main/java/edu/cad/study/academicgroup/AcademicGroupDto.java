package edu.cad.study.academicgroup;

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
    private int specialization;
    private int qualification;
    private int educationForm;
    private int workplan;
}
