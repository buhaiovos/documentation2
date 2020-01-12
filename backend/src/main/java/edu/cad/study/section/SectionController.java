package edu.cad.study.section;

import edu.cad.entities.Section;
import edu.cad.study.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/sections")
public class SectionController extends ActionController<Section, Integer, SectionDto> {
    public SectionController(SectionService service, SectionMapper mapper) {
        super(service, mapper);
    }
}
