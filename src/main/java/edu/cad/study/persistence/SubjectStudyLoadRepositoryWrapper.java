package edu.cad.study.persistence;

import edu.cad.entities.SubjectStudyLoad;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class SubjectStudyLoadRepositoryWrapper extends YearAwareRepositoryWrapper<SubjectStudyLoad, Integer> {
    public SubjectStudyLoadRepositoryWrapper(SubjectStudyLoadRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
