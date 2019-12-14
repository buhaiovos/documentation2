package edu.cad.study.curriculum;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurriculumDto {
    int id;
    String denotation;
    List<Integer> subjectIds;
    List<Integer> workingPlanIds;
}
