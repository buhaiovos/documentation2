package edu.cad.study.subject.header;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import edu.cad.study.DropdownOption;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record SubjectHeaderDto(
        int id,
        String denotation,
        DropdownOption superSubject,
        DropdownOption curriculumSection,
        DropdownOption workingPlanSection,
        Integer subjectTypeId,
        DropdownOption department
) {
    public SubjectHeaderDto {
        subjectTypeId = 1;
    }
}
