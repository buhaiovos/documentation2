package edu.cad.study.subject.info;

import edu.cad.entities.SubjectInfo;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubjectInfoRepositoryWrapper extends YearAwareRepositoryWrapper<SubjectInfo, Integer> {
    public SubjectInfoRepositoryWrapper(SubjectInfoRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
