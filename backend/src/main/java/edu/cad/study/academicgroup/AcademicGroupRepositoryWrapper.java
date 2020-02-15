package edu.cad.study.academicgroup;

import edu.cad.entities.AcademicGroup;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcademicGroupRepositoryWrapper extends YearAwareRepositoryWrapper<AcademicGroup, Integer> {
    private final AcademicGroupRepository repo;

    public AcademicGroupRepositoryWrapper(AcademicGroupRepository r, YearProvider p) {
        super(r, p);
        this.repo = r;
    }

    public List<AcademicGroup> findAllByIds(List<Integer> ids) {
        return repo.findAllById(ids);
    }
}

