package edu.cad.study.persistence;

import edu.cad.entities.DistributedOtherLoadInfo;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class DistributedOtherLoadInfoRepositoryWrapper
        extends YearAwareRepositoryWrapper<DistributedOtherLoadInfo, Integer> {
    public DistributedOtherLoadInfoRepositoryWrapper(DistributedOtherLoadInfoRepository repo,
                                                     YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
