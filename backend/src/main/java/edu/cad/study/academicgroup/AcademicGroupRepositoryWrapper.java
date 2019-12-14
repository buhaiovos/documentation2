package edu.cad.study.academicgroup;

import edu.cad.entities.AcademicGroup;
import edu.cad.study.persistence.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class AcademicGroupRepositoryWrapper extends YearAwareRepositoryWrapper<AcademicGroup, Integer> {
    public AcademicGroupRepositoryWrapper(AcademicGroupRepository r, YearProvider p) {
        super(r, p);
    }
}

