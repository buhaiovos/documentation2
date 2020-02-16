package edu.cad.study.cycle;

import edu.cad.entities.Cycle;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

@Component
public class CycleMapper implements EntityMapper<Cycle, CycleDto> {
    @Override
    public CycleDto toResponse(Cycle e) {
        return new CycleDto()
                .setId(e.getId())
                .setDenotation(e.getDenotation());
    }

    @Override
    public Option toOption(Cycle e) {
        return new Option(e.getDenotation(), e.getId());
    }
}
