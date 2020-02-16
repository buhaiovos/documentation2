package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.entities.CurriculumSubject;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class CurriculumMapper implements EntityMapper<Curriculum, CurriculumDto> {

    @Override
    public CurriculumDto toResponse(Curriculum e) {
        return new CurriculumDto()
                .setDenotation(e.getDenotation())
                .setId(e.getId())
                .setSubjectIdsByCiphers(
                        e.getCurriculumSubjects()
                                .stream()
                                .collect(
                                        Collectors.toMap(
                                                CurriculumSubject::getCipher,
                                                c -> c.getSubjectInfo().getId()
                                        )
                                )
                );
    }

    @Override
    public Option toOption(Curriculum e) {
        throw new UnsupportedOperationException("nothing to do here");
    }
}
