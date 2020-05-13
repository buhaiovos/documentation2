package edu.cad.study.control;

import edu.cad.entities.Control;
import edu.cad.study.ActionController;
import edu.cad.study.DropdownOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.cad.entities.ControlDictionary.*;

@RestController
@RequestMapping("/v2/controls")
@Slf4j
public class ControlController extends ActionController<Control, Integer, ControlDto> {
    public ControlController(ControlService service, ControlMapper mapper) {
        super(service, mapper);
    }

    @GetMapping("/types")
    public List<DropdownOption> getTypes() {
        log.info("Getting types of controls");
        return List.of(
                new DropdownOption(EXAM_ID, "Екзамен"),
                new DropdownOption(CREDIT_ID, "Залік"),
                new DropdownOption(MODULES_AND_TESTS_ID, "Контрольна робота"),
                new DropdownOption(COURSE_PROJECT, "Курсовий проект"),
                new DropdownOption(COURSE_WORK, "Курсова робота"),
                new DropdownOption(CALCULATION_GRAPHIC_ASSIGNMENT, "Розр. граф. робота"),
                new DropdownOption(STATE_TEST, "Державний екзамен"),
                new DropdownOption(ESSAY, "Реферат"),
                new DropdownOption(DIFFERENTIATED_CREDIT_ID, "Диференційний залік")
        );
    }
}
