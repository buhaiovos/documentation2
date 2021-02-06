package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.common.CipherAndTitledSubjectInfo;
import edu.cad.study.subject.info.SubjectInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class CurriculumSubjectService {
    private final SubjectInfoService subjectInfoService;

    public Set<CurriculumSubject> getCurriculumSubjects(Map<String, Integer> subjectIdsByCiphers,
                                                        Curriculum curriculum) {
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

    public Set<CurriculumSubject> getCurriculumSubjects(List<CipherAndTitledSubjectInfo> subjectsWithCiphers,
                                                        Curriculum curriculum) {
        return CollectionUtils.isEmpty(subjectsWithCiphers) ?
                new HashSet<>() : toCurriculumSubjectEntities(subjectsWithCiphers, curriculum);
    }

    private Set<CurriculumSubject> toCurriculumSubjectEntities(List<CipherAndTitledSubjectInfo> subjectsWithCiphers, Curriculum curriculum) {
        record CipherAndSubjectInfo(String cipher, SubjectInfo subjectInfo) {}

        return subjectsWithCiphers.stream()
                .map(ciphersAndSubject ->
                        new CipherAndSubjectInfo(
                                ciphersAndSubject.cipher(),
                                subjectInfoService.findById(ciphersAndSubject.id()).orElseThrow()
                        )
                )
                .map(cipherAndSubjectInfo -> {
                    var curriculumSubject = new CurriculumSubject();
                    curriculumSubject.setCipher(cipherAndSubjectInfo.cipher);
                    curriculumSubject.setCurriculum(curriculum);
                    curriculumSubject.setSubjectInfo(cipherAndSubjectInfo.subjectInfo);
                    return curriculumSubject;
                })
                .collect(toSet());
    }

    public SubjectInfoService subjectInfoService() {
        return this.subjectInfoService;
    }
}
