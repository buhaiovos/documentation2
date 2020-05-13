package edu.cad.study.load.other;

import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.OtherLoadType;
import edu.cad.entities.OtherLoad;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OtherLoadRepositoryWrapper extends YearAwareRepositoryWrapper<OtherLoad, Integer> {
    private final YearProvider yearProvider;
    private final OtherLoadRepository repo;

    public OtherLoadRepositoryWrapper(OtherLoadRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
        this.repo = repo;
        this.yearProvider = yearProvider;
    }

    public Optional<OtherLoad> findByLoadTypeAndObjectOfWork(OtherLoadType loadType,
                                                             ObjectOfWork objectOfWork) {
        return repo.findByLoadTypeAndObjectOfWorkAndYearOfInformation(
                loadType,
                objectOfWork,
                yearProvider.getCurrent()
        );
    }

}
