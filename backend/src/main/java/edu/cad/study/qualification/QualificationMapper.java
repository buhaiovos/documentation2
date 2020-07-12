package edu.cad.study.qualification;

import edu.cad.entities.Qualification;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

@Component
class QualificationMapper implements EntityMapper<Qualification, QualificationDto> {
    public QualificationDto toResponse(Qualification q) {
        return new QualificationDto(
                q.getId(),
                q.getDenotation()
        );
    }

    public DropdownOption toOption(Qualification q) {
        return new DropdownOption(q.getId(), q.getDenotation());
    }
}
