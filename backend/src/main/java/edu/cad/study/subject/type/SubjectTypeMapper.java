package edu.cad.study.subject.type;

import edu.cad.entities.SubjectType;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class SubjectTypeMapper implements EntityMapper<SubjectType, SubjectTypeDto> {

    @Override
    public SubjectTypeDto toResponse(SubjectType e) {
        return new SubjectTypeDto()
                .setDenotation(e.getDenotation())
                .setId(e.getId());
    }

    @Override
    public DropdownOption toOption(SubjectType e) {
        throw new UnsupportedOperationException("meh");
    }
}
