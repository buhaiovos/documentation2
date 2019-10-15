package edu.cad.study.persistence;

import edu.cad.entities.Cycle;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class CycleRepositoryWrapper extends YearAwareRepositoryWrapper<Cycle, Integer> {
    public CycleRepositoryWrapper(CycleRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
