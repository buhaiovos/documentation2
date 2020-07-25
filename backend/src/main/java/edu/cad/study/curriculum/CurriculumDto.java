package edu.cad.study.curriculum;

import edu.cad.study.common.CipherAndTitledSubjectInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurriculumDto {
    int id;
    String denotation;
    Map<String, Integer> subjectIdsByCiphers;
    List<CipherAndTitledSubjectInfo> subjectsWithCiphers;
}
