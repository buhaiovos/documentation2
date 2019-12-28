package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class CurriculumRepositoryWrapper extends YearAwareRepositoryWrapper<Curriculum, Integer> {
    public CurriculumRepositoryWrapper(CurriculumRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
