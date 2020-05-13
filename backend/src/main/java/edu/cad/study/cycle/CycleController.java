package edu.cad.study.cycle;

import edu.cad.entities.Cycle;
import edu.cad.study.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/cycles")
public class CycleController extends ActionController<Cycle, Integer, CycleDto> {
    public CycleController(CycleService service, CycleMapper mapper) {
        super(service, mapper);
    }
}
