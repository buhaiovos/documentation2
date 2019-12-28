package edu.cad.study.load.other;

import edu.cad.entities.DistributedOtherLoadInfo;
import edu.cad.study.YearAwareRepositoryWrapper;
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
