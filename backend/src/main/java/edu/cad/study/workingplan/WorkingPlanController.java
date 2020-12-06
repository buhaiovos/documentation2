package edu.cad.study.workingplan;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.ActionController;
import edu.cad.study.DropdownOption;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/working-plans")
public class WorkingPlanController extends ActionController<WorkingPlan, Integer, WorkingPlanDto> {
    private final WorkingPlanService service;
    private final WorkingPlanMapper mapper;

    public WorkingPlanController(WorkingPlanService service, WorkingPlanMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }

    @PatchMapping("/{id}/diploma-preparations")
    public WorkingPlanDto addNewDiplomaPreparation(@PathVariable int id,
                                                   @RequestBody DropdownOption diplomaPreparation) {
        return mapper.toResponse(service.addDiplomaPreparation(id, diplomaPreparation));
    }
}
