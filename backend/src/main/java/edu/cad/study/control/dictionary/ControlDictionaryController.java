package edu.cad.study.control.dictionary;

import edu.cad.entities.ControlDictionary;
import edu.cad.study.ActionController;
import org.springframework.stereotype.Component;

@Component
public class ControlDictionaryController extends ActionController<ControlDictionary, Integer, ControlDictionaryDto> {
    public ControlDictionaryController(ControlDictionaryService service, ControlDictionaryMapper mapper) {
        super(service, mapper);
    }
}
