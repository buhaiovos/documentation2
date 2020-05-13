package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.study.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/curricula")
public class CurriculumController extends ActionController<Curriculum, Integer, CurriculumDto> {
    public CurriculumController(CurriculumService service, CurriculumMapper mapper) {
        super(service, mapper);
    }
}
