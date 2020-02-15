package edu.cad.study.subject.header;

import edu.cad.entities.SubjectHeader;
import edu.cad.study.EntityService;
import edu.cad.study.department.DepartmentService;
import edu.cad.study.section.SectionService;
import edu.cad.study.subject.type.SubjectTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class SubjectHeaderService implements EntityService<SubjectHeader, Integer, SubjectHeaderDto> {
    private final SubjectHeaderRepositoryWrapper repo;

    private final SectionService sectionService;
    private final SubjectTypeService subjectTypeService;
    private final DepartmentService departmentService;

    @Override
    public List<SubjectHeader> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<SubjectHeader> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public SubjectHeader create(SubjectHeaderDto dto) {
        var newSubjectHeader = new SubjectHeader();
        setFieldsFromDto(dto, newSubjectHeader);

        return repo.save(newSubjectHeader);
    }

    @Override
    public SubjectHeader update(Integer id, SubjectHeaderDto dto) {
        var updated = repo.findById(id).orElseThrow();
        setFieldsFromDto(dto, updated);

        return updated;
    }

    private void setFieldsFromDto(SubjectHeaderDto dto, SubjectHeader entity) {
        entity.setDenotation(dto.getDenotation());

        var subjectType = subjectTypeService.findById(dto.getSubjectTypeId())
                .orElseThrow();
        entity.setType(subjectType);

        var department = departmentService.findById(dto.getDepartmentId())
                .orElseThrow();
        entity.setDepartment(department);

        ofNullable(dto.getSuperSubjectId()).ifPresent(
                id -> entity.setSuperSubject(
                        repo.findById(id).orElseThrow()
                )
        );
        ofNullable(dto.getCurriculumSectionId()).ifPresent(
                id -> entity.setCurriculumSection(
                        sectionService.findById(id).orElseThrow()
                )
        );
        ofNullable(dto.getWorkingPlanSectionId()).ifPresent(
                id -> entity.setWorkingPlanSection(
                        sectionService.findById(id).orElseThrow()
                )
        );
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
