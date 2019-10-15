package edu.cad.study.persistence;

import edu.cad.entities.Control;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class ControlRepositoryWrapper extends YearAwareRepositoryWrapper<Control, Integer> {
    public ControlRepositoryWrapper(ControlRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
