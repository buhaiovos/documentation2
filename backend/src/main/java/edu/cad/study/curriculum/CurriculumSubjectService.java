package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.subject.info.SubjectInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class CurriculumSubjectService {
    private final SubjectInfoService subjectInfoService;

    public Set<CurriculumSubject> getCurriculumSubjects(Map<String, Integer> subjectIdsByCiphers, Curriculum curriculum) {
        Map<Integer, SubjectInfo> subjectsById =
                subjectInfoService.getByIds(List.copyOf(subjectIdsByCiphers.values()))
                        .stream()
                        .collect(toMap(SubjectInfo::getId, val -> val));

        return subjectIdsByCiphers.entrySet()
                .stream()
                .map(entry -> {
                    var curriculumSubject = new CurriculumSubject();
                    curriculumSubject.setCipher(entry.getKey());
                    curriculumSubject.setCurriculum(curriculum);
                    curriculumSubject.setSubjectInfo(subjectsById.get(entry.getValue()));
                    return curriculumSubject;
                })
                .collect(toSet());
    }

    public SubjectInfoService subjectInfoService() {
        return this.subjectInfoService;
    }
}