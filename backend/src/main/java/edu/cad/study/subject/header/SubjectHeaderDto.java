package edu.cad.study.subject.header;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SubjectHeaderDto {
    private int id;
    private String denotation;
    private Integer superSubjectId;
    private Integer curriculumSectionId;
    private Integer workingPlanSectionId;
    private Integer subjectTypeId;
    private Integer departmentId;
}
