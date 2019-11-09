package edu.cad.study.service;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.persistence.WorkingPlanRepositoryWrapper;
import edu.cad.study.web.workingplan.WorkingPlanDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkingPlanService implements EntityService<WorkingPlan, Integer, WorkingPlanDto> {
    WorkingPlanRepositoryWrapper repository;

    public List<WorkingPlan> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<WorkingPlan> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public WorkingPlan create(WorkingPlanDto academicGroup) {
        return null;
    }

    @Override
    public WorkingPlan update(WorkingPlanDto updatedGroup) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
