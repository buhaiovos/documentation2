package edu.cad.study.persistence;

import edu.cad.entities.SubjectInfo;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class SubjectInfoRepositoryWrapper extends YearAwareRepositoryWrapper<SubjectInfo, Integer> {
    public SubjectInfoRepositoryWrapper(SubjectInfoRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
