package edu.cad.study.workingplan;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkingPlanDto {
    int id;
    String denotation;
    int curriculumId;
    int practiceId;
    int stateCertificationId;
    int scientificResearchSubjectId;
    Map<String, Integer> subjectIdsByCiphers;
    List<Integer> groupIds;
    List<Integer> diplomaPreparationIds;
}
