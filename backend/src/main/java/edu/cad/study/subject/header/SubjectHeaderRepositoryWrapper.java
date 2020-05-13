package edu.cad.study.subject.header;

import edu.cad.entities.SubjectHeader;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class SubjectHeaderRepositoryWrapper extends YearAwareRepositoryWrapper<SubjectHeader, Integer> {
    public SubjectHeaderRepositoryWrapper(SubjectHeaderRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
