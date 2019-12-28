package edu.cad.study.load.subject;

import edu.cad.entities.SubjectStudyLoad;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class SubjectStudyLoadRepositoryWrapper extends YearAwareRepositoryWrapper<SubjectStudyLoad, Integer> {
    public SubjectStudyLoadRepositoryWrapper(SubjectStudyLoadRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
