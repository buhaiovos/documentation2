package edu.cad.study.workingplan;

import edu.cad.study.DropdownOption;
import edu.cad.study.curriculum.CurriculumDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkingPlanDto extends CurriculumDto {
    DropdownOption curriculum;
    DropdownOption practice;
    DropdownOption stateCertification;
    DropdownOption scientificResearchSubject;
    List<DropdownOption> groups;
    List<DropdownOption> diplomaPreparations;
}
