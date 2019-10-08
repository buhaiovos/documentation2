package edu.cad.study.service;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.persistence.WorkingPlanRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkingPlanService {
    WorkingPlanRepository repository;

    public List<WorkingPlan> getAll() {
        return repository.findAll();
    }
}
