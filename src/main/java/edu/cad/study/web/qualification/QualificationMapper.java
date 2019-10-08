package edu.cad.study.web.qualification;

import edu.cad.entities.Qualification;
import edu.cad.utils.gson.Option;
import org.springframework.stereotype.Component;

@Component
class QualificationMapper {
    QualificationDto toResponse(Qualification q) {
        return new QualificationDto();
    }

    Option toOption(Qualification q) {
        return new Option(q.getDenotation(), q.getId());
    }
}
