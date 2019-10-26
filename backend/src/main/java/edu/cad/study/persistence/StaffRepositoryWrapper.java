package edu.cad.study.persistence;

import edu.cad.entities.Staff;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class StaffRepositoryWrapper extends YearAwareRepositoryWrapper<Staff, Integer> {
    public StaffRepositoryWrapper(StaffRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
