package edu.cad.study.workingplan;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/working-plans")
public class WorkingPlanController extends ActionController<WorkingPlan, Integer, WorkingPlanDto> {
    public WorkingPlanController(WorkingPlanService service, WorkingPlanMapper mapper) {
        super(service, mapper);
    }
}
