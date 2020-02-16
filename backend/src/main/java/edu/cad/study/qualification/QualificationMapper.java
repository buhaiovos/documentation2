package edu.cad.study.qualification;

import edu.cad.entities.Qualification;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

@Component
class QualificationMapper implements EntityMapper<Qualification, QualificationDto> {
    public QualificationDto toResponse(Qualification q) {
        return new QualificationDto();
    }

    public Option toOption(Qualification q) {
        return new Option(q.getDenotation(), q.getId());
    }
}
