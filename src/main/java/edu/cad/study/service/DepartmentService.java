package edu.cad.study.service;

import edu.cad.entities.Department;
import edu.cad.study.web.department.DepartmentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements EntityService<Department, Integer, DepartmentDto> {
    @Override
    public List<Department> getAll() {
        return null;
    }

    @Override
    public Optional<Department> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Department create(DepartmentDto academicGroup) {
        return null;
    }

    @Override
    public Department update(DepartmentDto updatedGroup) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
