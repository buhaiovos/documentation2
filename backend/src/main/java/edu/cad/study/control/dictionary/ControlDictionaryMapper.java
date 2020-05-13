package edu.cad.study.control.dictionary;

import edu.cad.entities.ControlDictionary;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ControlDictionaryMapper implements EntityMapper<ControlDictionary, ControlDictionaryDto> {

    @Override
    public ControlDictionaryDto toResponse(ControlDictionary cd) {
        return new ControlDictionaryDto()
                .setDenotation(cd.getDenotation())
                .setId(cd.getId());
    }

    @Override
    public DropdownOption toOption(ControlDictionary cd) {
        return new DropdownOption(cd.getId(), cd.getDenotation());
    }
}
