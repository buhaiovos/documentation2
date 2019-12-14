package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.study.ActionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/academic-groups")
@Slf4j
public class CurriculumController extends ActionController<Curriculum, Integer, CurriculumDto> {
    public CurriculumController(CurriculumService service, CurriculumMapper mapper) {
        super(service, mapper);
    }
}
