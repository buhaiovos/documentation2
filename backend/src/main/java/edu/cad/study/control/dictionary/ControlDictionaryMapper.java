package edu.cad.study.control.dictionary;

import edu.cad.entities.ControlDictionary;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
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
    public Option toOption(ControlDictionary cd) {
        return new Option(cd.getDenotation(), cd.getId());
    }
}
