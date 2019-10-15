package edu.cad.study.persistence;

import edu.cad.entities.OtherLoadInfo;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class OtherLoadInfoRepositoryWrapper extends YearAwareRepositoryWrapper<OtherLoadInfo, Integer> {
    public OtherLoadInfoRepositoryWrapper(OtherLoadInfoRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
