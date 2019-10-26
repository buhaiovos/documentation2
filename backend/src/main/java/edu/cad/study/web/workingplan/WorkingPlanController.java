package edu.cad.study.web.workingplan;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.service.WorkingPlanService;
import edu.cad.study.web.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v2/working-plans")
public class WorkingPlanController extends ActionController<WorkingPlan, Integer, WorkingPlanDto> {
    public WorkingPlanController(WorkingPlanService service, WorkingPlanMapper mapper) {
        super(service, mapper);
    }

    @Override
    public List<WorkingPlanDto> getDependent(HttpServletRequest request) {
        return null;
    }
}
