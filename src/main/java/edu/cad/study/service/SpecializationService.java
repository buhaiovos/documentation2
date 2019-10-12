package edu.cad.study.service;

import edu.cad.entities.Department;
import edu.cad.entities.Specialization;
import edu.cad.study.persistence.SpecializationRepositoryWrapper;
import edu.cad.study.web.specialization.SpecializationDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecializationService implements EntityService<Specialization, Integer, SpecializationDto> {
    SpecializationRepositoryWrapper repo;
    DepartmentService departmentService;

    @Override
    public List<Specialization> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Specialization> findById(Integer integer) {
        return repo.findById(integer);
    }

    @Override
    public Specialization create(SpecializationDto specialization) {
        Department department = departmentService
                .findById(specialization.getDepartment())
                .orElseThrow();

        var newSpecialization = new Specialization()
                .setDepartment(department)
                .setDenotation(specialization.getDenotation());
        return repo.save(newSpecialization);
    }

    @Override
    public Specialization update(SpecializationDto updatedGroup) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
