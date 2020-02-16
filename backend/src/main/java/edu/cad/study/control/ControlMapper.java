package edu.cad.study.control;

import edu.cad.entities.Control;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

@Component
class ControlMapper implements EntityMapper<Control, ControlDto> {

    @Override
    public ControlDto toResponse(Control e) {
        return new ControlDto()
                .setId(e.getId())
                .setSemester(e.getSemester())
                .setTypeId(e.getType().getId())
                .setSubjectId(e.getSubjectInfo().getId());
    }

    @Override
    public Option toOption(Control e) {
        throw new UnsupportedOperationException("nothing to do here");
    }
}
