package edu.cad.study.specialization;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.EntityService;
import edu.cad.study.workingplan.WorkingPlanDto;
import edu.cad.study.workingplan.WorkingPlanRepositoryWrapper;
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
