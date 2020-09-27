package edu.cad.study.curriculum;

import edu.cad.study.common.CipherAndTitledSubjectInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class CurriculumDto {
    int id;
    String denotation;
    List<CipherAndTitledSubjectInfo> subjectsWithCiphers;
}
