package edu.cad.study.web.academicgroup;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
class AcademicGroupDto {
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
