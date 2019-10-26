package edu.cad.study.persistence;

import edu.cad.entities.WorkingPlan;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class WorkingPlanRepositoryWrapper extends YearAwareRepositoryWrapper<WorkingPlan, Integer> {
    public WorkingPlanRepositoryWrapper(WorkingPlanRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
