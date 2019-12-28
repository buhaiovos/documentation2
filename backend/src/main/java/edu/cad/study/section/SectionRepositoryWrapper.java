package edu.cad.study.section;

import edu.cad.entities.Section;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class SectionRepositoryWrapper extends YearAwareRepositoryWrapper<Section, Integer> {
    public SectionRepositoryWrapper(SectionRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
