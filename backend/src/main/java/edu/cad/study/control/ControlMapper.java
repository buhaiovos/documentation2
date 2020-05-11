package edu.cad.study.control;

import edu.cad.entities.Control;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

import static edu.cad.utils.Utils.nullOr;

@Component
public class ControlMapper implements EntityMapper<Control, ControlDto> {

    @Override
    public ControlDto toResponse(Control e) {
        return new ControlDto(
                e.getId(),
                e.getSemester(),
                nullOr(e.getType(), ControlDictionary::getId),
                nullOr(e.getType(), ControlDictionary::getDenotation),
                nullOr(e.getSubjectInfo(), SubjectInfo::getId)
        );
    }

    @Override
    public Option toOption(Control e) {
        throw new UnsupportedOperationException("nothing to do here");
    }
}
