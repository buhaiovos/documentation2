package edu.cad.study.load.subject;

import edu.cad.entities.DistributedSubjectStudyLoad;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class DistributedSubjectStudyLoadRepositoryWrapper
        extends YearAwareRepositoryWrapper<DistributedSubjectStudyLoad, Integer> {
    public DistributedSubjectStudyLoadRepositoryWrapper(DistributedSubjectStudyLoadRepository repo,
                                                        YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
