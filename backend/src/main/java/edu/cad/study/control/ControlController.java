package edu.cad.study.control;

import edu.cad.entities.Control;
import edu.cad.study.ActionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/controls")
@Slf4j
public class ControlController extends ActionController<Control, Integer, ControlDto> {
    public ControlController(ControlService service, ControlMapper mapper) {
        super(service, mapper);
    }
}
