package edu.cad.study.worktype;

import edu.cad.entities.WorkType;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class WorkTypeRepositoryWrapper extends YearAwareRepositoryWrapper<WorkType, Integer> {
    public WorkTypeRepositoryWrapper(WorkTypeRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
