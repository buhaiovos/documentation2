package edu.cad.study.workingplan;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

@Component
class WorkingPlanMapper implements EntityMapper<WorkingPlan, WorkingPlanDto> {
    public WorkingPlanDto toResponse(WorkingPlan wp) {
        return new WorkingPlanDto()
                .setId(0);
    }

    public Option toOption(WorkingPlan wp) {
        return new Option(
                wp.getDenotation(),
                wp.getId()
        );
    }
}
