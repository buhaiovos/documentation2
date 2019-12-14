package edu.cad.study.department;

import edu.cad.entities.Department;
import edu.cad.study.EntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DepartmentService implements EntityService<Department, Integer, DepartmentDto> {
    DepartmentRepositoryWrapper repository;

    @Override
    public List<Department> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Department> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Department create(DepartmentDto department) {
        var newDepartment = new Department();
        newDepartment.setDenotation(department.getDenotation());
        return repository.save(newDepartment);
    }

    @Override
    public Department update(DepartmentDto updatedGroup) {
        Department existing = repository.findById(updatedGroup.getId()).orElseThrow();
        existing.setDenotation(updatedGroup.getDenotation());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
