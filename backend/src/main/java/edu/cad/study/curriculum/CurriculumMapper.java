package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.entities.CurriculumSubject;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import edu.cad.study.common.CipherAndTitledSubjectInfo;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Component
class CurriculumMapper implements EntityMapper<Curriculum, CurriculumDto> {

    @Override
    public CurriculumDto toResponse(Curriculum e) {
        return new CurriculumDto()
                .setDenotation(e.getDenotation())
                .setId(e.getId())
                .setSubjectIdsByCiphers(getSubjectByCiphers(e))
                .setSubjectsWithCiphers(getSubjectsWithCiphers(e));
    }

    private Map<String, Integer> getSubjectByCiphers(Curriculum e) {
        return e.getCurriculumSubjects().stream()
                .collect(
                        Collectors.toMap(
                                CurriculumSubject::getCipher,
                                c -> c.getSubjectInfo().getId()
                        )
                );
    }

    private List<CipherAndTitledSubjectInfo> getSubjectsWithCiphers(Curriculum c) {
        return c.getCurriculumSubjects().stream()
                .map(sc -> {
                    var subjectInfo = sc.getSubjectInfo();
                    return new CipherAndTitledSubjectInfo(
                            sc.getCipher(),
                            subjectInfo.getId(),
                            subjectInfo.getSubjectHeader().getDenotation(),
                            subjectInfo.getSemester(),
                            subjectInfo.getEcts(),
                            subjectInfo.getLectures(),
                            subjectInfo.getPractices(),
                            subjectInfo.getLabs()
                    );
                })
                .sorted(Comparator.comparing(CipherAndTitledSubjectInfo::cipher))
                .collect(toUnmodifiableList());
    }

    @Override
    public DropdownOption toOption(Curriculum e) {
        return new DropdownOption(e.getId(), e.getDenotation());
    }
}
