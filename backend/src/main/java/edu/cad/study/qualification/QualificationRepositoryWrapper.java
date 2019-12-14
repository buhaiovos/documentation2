package edu.cad.study.qualification;

import edu.cad.entities.Qualification;
import edu.cad.study.persistence.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class QualificationRepositoryWrapper extends YearAwareRepositoryWrapper<Qualification, Integer> {
    public QualificationRepositoryWrapper(QualificationRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
