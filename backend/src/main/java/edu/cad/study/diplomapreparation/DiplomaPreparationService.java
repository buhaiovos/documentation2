package edu.cad.study.diplomapreparation;

import edu.cad.entities.Department;
import edu.cad.entities.DiplomaPreparation;
import edu.cad.entities.WorkType;
import edu.cad.entities.WorkingPlan;
import edu.cad.study.EntityService;
import edu.cad.study.department.DepartmentService;
import edu.cad.study.workingplan.WorkingPlanService;
import edu.cad.study.worktype.WorkTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiplomaPreparationService implements EntityService<DiplomaPreparation, Integer, DiplomaPreparationDto> {
    DiplomaPreparationRepositoryWrapper repo;
    DepartmentService departmentService;
    WorkTypeService workTypeService;
    WorkingPlanService workingPlanService;

    @Override
    public List<DiplomaPreparation> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<DiplomaPreparation> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public DiplomaPreparation create(DiplomaPreparationDto dto) {
        Department department =
                departmentService.findById(dto.getDepartmentId()).orElseThrow();
        WorkType workType =
                workTypeService.findById(dto.getWorkTypeId()).orElseThrow();
        WorkingPlan workingPlan =
                workingPlanService.findById(dto.getWorkingPlanId()).orElseThrow();

        var newDiplomaPreparation = new DiplomaPreparation()
                .setNorm(dto.getNorm())
                .setDepartment(department)
                .setWorkType(workType)
                .setWorkingPlan(workingPlan);

        return repo.save(newDiplomaPreparation);
    }

    @Override
    public DiplomaPreparation update(Integer id, DiplomaPreparationDto dto) {
        DiplomaPreparation updated = repo.findById(id).orElseThrow();
        Department department =
                departmentService.findById(dto.getDepartmentId()).orElseThrow();
        WorkType workType =
                workTypeService.findById(dto.getWorkTypeId()).orElseThrow();
        WorkingPlan workingPlan =
                workingPlanService.findById(dto.getWorkingPlanId()).orElseThrow();

        updated.setNorm(dto.getNorm())
                .setDepartment(department)
                .setWorkType(workType)
                .setWorkingPlan(workingPlan);

        return updated;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    public List<DiplomaPreparation> findAllByIds(List<Integer> ids) {
        return repo.findAllByIdIn(ids);
    }
}
