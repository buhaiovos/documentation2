package edu.cad.study.section;

import edu.cad.entities.Section;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

import static edu.cad.utils.Utils.nullOr;

@Component
public class SectionMapper implements EntityMapper<Section, SectionDto> {
    @Override
    public SectionDto toResponse(Section e) {
        return new SectionDto(
                e.getId(),
                e.getDenotation(),
                e.isOptional(),
                nullOr(e.getCycle(), cycle -> new DropdownOption(cycle.getId(), cycle.getDenotation()))
        );
    }

    @Override
    public DropdownOption toOption(Section e) {
        return new DropdownOption(e.getId(), e.getDenotation());
    }
}
