package edu.cad.study.practice;

import edu.cad.entities.Practice;
import edu.cad.study.EntityMapper;
import edu.cad.utils.gson.Option;
import org.springframework.stereotype.Component;

@Component
public class PracticeMapper implements EntityMapper<Practice, PracticeDto> {
    @Override
    public PracticeDto toResponse(Practice e) {
        return new PracticeDto()
                .setId(e.getId())
                .setSemester(e.getSemester())
                .setWeeks(e.getWeeks())
                .setType(e.getType().getDenotation())
                .setStart(e.getStart())
                .setFinish(e.getFinish());
    }

    @Override
    public Option toOption(Practice e) {
        throw new UnsupportedOperationException();
    }
}
