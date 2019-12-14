package edu.cad.study.workingplan;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkingPlanDto {
    int id;
    String denotation;
    int practiceId;
    int stateCertificationId;
    int curriculumId;
    List<Integer> subjectIds;
    List<Integer> groupIds;
    List<Integer> diplomaPreparationIds;
    int scientificResearchSubjectId;
}
