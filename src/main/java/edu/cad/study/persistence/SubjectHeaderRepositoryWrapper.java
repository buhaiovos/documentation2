package edu.cad.study.persistence;

import edu.cad.entities.SubjectHeader;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class SubjectHeaderRepositoryWrapper extends YearAwareRepositoryWrapper<SubjectHeader, Integer> {
    public SubjectHeaderRepositoryWrapper(SubjectHeaderRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
