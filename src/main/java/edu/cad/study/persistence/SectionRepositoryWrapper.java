package edu.cad.study.persistence;

import edu.cad.entities.Section;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class SectionRepositoryWrapper extends YearAwareRepositoryWrapper<Section, Integer> {
    public SectionRepositoryWrapper(SectionRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
