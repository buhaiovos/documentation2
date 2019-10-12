package edu.cad.study.persistence;

import edu.cad.entities.Specialization;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class SpecializationRepositoryWrapper extends YearAwareRepositoryWrapper<Specialization, Integer> {
    public SpecializationRepositoryWrapper(SpecializationRepository r, YearProvider p) {
        super(r, p);
    }
}
