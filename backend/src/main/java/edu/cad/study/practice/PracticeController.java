package edu.cad.study.practice;

import edu.cad.entities.Practice;
import edu.cad.study.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/practices")
public class PracticeController extends ActionController<Practice, Integer, PracticeDto> {
    public PracticeController(PracticeService service, PracticeMapper mapper) {
        super(service, mapper);
    }
}
