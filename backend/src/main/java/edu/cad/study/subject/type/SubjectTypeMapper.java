package edu.cad.study.subject.type;

import edu.cad.entities.SubjectType;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
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
    public Option toOption(SubjectType e) {
        throw new UnsupportedOperationException("meh");
    }
}
