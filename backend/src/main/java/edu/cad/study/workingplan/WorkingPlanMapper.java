package edu.cad.study.workingplan;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

@Component
class WorkingPlanMapper implements EntityMapper<WorkingPlan, WorkingPlanDto> {
    public WorkingPlanDto toResponse(WorkingPlan wp) {
        return new WorkingPlanDto()
                .setId(0);
    }

    public DropdownOption toOption(WorkingPlan wp) {
        return new DropdownOption(wp.getId(), wp.getDenotation());
    }
}
