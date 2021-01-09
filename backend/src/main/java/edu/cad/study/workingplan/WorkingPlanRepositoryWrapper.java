package edu.cad.study.workingplan;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class WorkingPlanRepositoryWrapper extends YearAwareRepositoryWrapper<WorkingPlan, Integer> {
    private static final String WORKING_PLAN = "workplan";

    private final WorkingPlanRepository repo;
    private final YearProvider yearProvider;

    public WorkingPlanRepositoryWrapper(WorkingPlanRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
        this.repo = repo;
        this.yearProvider = yearProvider;
    }

    @Override
    public List<WorkingPlan> findAll() {
        return repo.findAllByTypeAndYear(WORKING_PLAN, yearProvider.getCurrent());
    }
}
