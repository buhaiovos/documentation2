package edu.cad.study.practice;

import edu.cad.entities.Practice;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class PracticeRepositoryWrapper extends YearAwareRepositoryWrapper<Practice, Integer> {
    public PracticeRepositoryWrapper(PracticeRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
