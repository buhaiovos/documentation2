package edu.cad.study.diplomapreparation;

import edu.cad.entities.DiplomaPreparation;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiplomaPreparationRepositoryWrapper extends YearAwareRepositoryWrapper<DiplomaPreparation, Integer> {
    private final DiplomaPreparationRepository repo;

    public DiplomaPreparationRepositoryWrapper(DiplomaPreparationRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
        this.repo = repo;
    }

    public List<DiplomaPreparation> findAllByIdIn(List<Integer> ids) {
        return repo.findAllById(ids);
    }
}
