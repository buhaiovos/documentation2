package edu.cad.study.persistence;

import edu.cad.entities.WorkType;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class WorkTypeRepositoryWrapper extends YearAwareRepositoryWrapper<WorkType, Integer> {
    public WorkTypeRepositoryWrapper(WorkTypeRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
