package edu.cad.study.diplomapreparation;

import edu.cad.entities.Department;
import edu.cad.entities.DiplomaPreparation;
import edu.cad.entities.WorkType;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityService;
import edu.cad.study.department.DepartmentService;
import edu.cad.study.worktype.WorkTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiplomaPreparationService implements EntityService<DiplomaPreparation, Integer, DiplomaPreparationDto> {
    DiplomaPreparationRepositoryWrapper repo;
    DepartmentService departmentService;
    WorkTypeService workTypeService;

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
        var newDiplomaPreparation = new DiplomaPreparation()
                .setNorm(dto.getNorm());

        ofNullable(dto.getDepartment())
                .map(DropdownOption::id)
                .flatMap(departmentService::findById)
                .ifPresent(newDiplomaPreparation::setDepartment);

        ofNullable(dto.getWorkType())
                .map(DropdownOption::id)
                .flatMap(workTypeService::findById)
                .ifPresent(newDiplomaPreparation::setWorkType);

        return repo.save(newDiplomaPreparation);
    }

    @Override
    public DiplomaPreparation update(Integer id, DiplomaPreparationDto dto) {
        DiplomaPreparation updated = repo.findById(id).orElseThrow();
        Department department =
                departmentService.findById(dto.getDepartment().id()).orElseThrow();
        WorkType workType =
                workTypeService.findById(dto.getWorkType().id()).orElseThrow();

        updated.setNorm(dto.getNorm())
                .setDepartment(department)
                .setWorkType(workType);

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
