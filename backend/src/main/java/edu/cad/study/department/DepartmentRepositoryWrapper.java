package edu.cad.study.department;

import edu.cad.entities.Department;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class DepartmentRepositoryWrapper extends YearAwareRepositoryWrapper<Department, Integer> {
    public DepartmentRepositoryWrapper(DepartmentRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
