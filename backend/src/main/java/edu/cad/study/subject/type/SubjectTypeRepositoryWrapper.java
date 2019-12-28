package edu.cad.study.subject.type;

import edu.cad.entities.SubjectType;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class SubjectTypeRepositoryWrapper extends YearAwareRepositoryWrapper<SubjectType, Integer> {
    public SubjectTypeRepositoryWrapper(SubjectTypeRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
