package edu.cad.study.persistence;

import edu.cad.entities.OtherLoad;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class OtherLoadRepositoryWrapper extends YearAwareRepositoryWrapper<OtherLoad, Integer> {
    public OtherLoadRepositoryWrapper(OtherLoadRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
