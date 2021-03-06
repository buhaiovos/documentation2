package edu.cad.study.workingplan;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.ActionController;
import edu.cad.study.DropdownOption;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v2/working-plans")
class WorkingPlanController extends ActionController<WorkingPlan, Integer, WorkingPlanDto> {
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

    @GetMapping("/{id}/subjects")
    public List<DropdownOption> getWorkingPlanSubjects(@PathVariable Integer id) {
        final List<DropdownOption> subjects = service.getAllWorkingPlanSubjects(id);
        final List<DropdownOption> subjectsWithEmptyOption = new ArrayList<>(subjects.size() + 1);
        subjectsWithEmptyOption.add(DropdownOption.empty());
        subjectsWithEmptyOption.addAll(subjects);
        return subjectsWithEmptyOption;
    }
}
