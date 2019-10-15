package edu.cad.study.persistence;

import edu.cad.entities.StateCertification;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class StateCertificationRepositoryWrapper extends YearAwareRepositoryWrapper<StateCertification, Integer> {
    public StateCertificationRepositoryWrapper(StateCertificationRepository repo,
                                               YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
