package edu.cad.study.load.other;

import edu.cad.entities.OtherLoad;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class OtherLoadRepositoryWrapper extends YearAwareRepositoryWrapper<OtherLoad, Integer> {
    public OtherLoadRepositoryWrapper(OtherLoadRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
