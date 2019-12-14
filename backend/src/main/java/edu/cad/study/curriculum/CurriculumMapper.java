package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.study.EntityMapper;
import edu.cad.utils.gson.Option;
import org.springframework.stereotype.Component;

@Component
class CurriculumMapper implements EntityMapper<Curriculum, CurriculumDto> {

    @Override
    public CurriculumDto toResponse(Curriculum e) {
        return null;
    }

    @Override
    public Option toOption(Curriculum e) {
        throw new UnsupportedOperationException("nothing to do here");
    }
}
