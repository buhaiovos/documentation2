package edu.cad.study.section;

import edu.cad.entities.Cycle;
import edu.cad.entities.Section;
import edu.cad.study.EntityMapper;
import edu.cad.utils.gson.Option;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class SectionMapper implements EntityMapper<Section, SectionDto> {
    @Override
    public SectionDto toResponse(Section e) {
        return new SectionDto()
                .setId(e.getId())
                .setDenotation(e.getDenotation())
                .setOptional(e.isOptional())
                .setCycleId(ofNullable(e.getCycle()).map(Cycle::getId).orElseThrow());
    }

    @Override
    public Option toOption(Section e) {
        return null;
    }
}
