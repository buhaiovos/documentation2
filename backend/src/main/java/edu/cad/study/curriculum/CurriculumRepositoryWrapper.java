package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurriculumRepositoryWrapper extends YearAwareRepositoryWrapper<Curriculum, Integer> {
    private static final String CURRICULUM = "curriculum";

    private CurriculumRepository repo;
    private YearProvider yearProvider;

    public CurriculumRepositoryWrapper(CurriculumRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
        this.repo = repo;
        this.yearProvider = yearProvider;
    }

    @Override
    public List<Curriculum> findAll() {
        return repo.findAllByTypeAndYear(CURRICULUM, yearProvider.getCurrent());
    }
}

