package edu.cad.study.workingplan;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.EntityService;
import edu.cad.study.academicgroup.AcademicGroupService;
import edu.cad.study.curriculum.CurriculumService;
import edu.cad.study.curriculum.CurriculumSubjectService;
import edu.cad.study.diplomapreparation.DiplomaPreparationService;
import edu.cad.study.practice.PracticeService;
import edu.cad.study.statecert.StateCertificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkingPlanService implements EntityService<WorkingPlan, Integer, WorkingPlanDto> {
    WorkingPlanRepositoryWrapper repository;
    CurriculumService curriculumService;
    StateCertificationService certificationService;
    PracticeService practiceService;
    AcademicGroupService academicGroupService;
    DiplomaPreparationService diplomaPreparationService;
    CurriculumSubjectService curriculumSubjectService;

    @Override
    public List<WorkingPlan> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<WorkingPlan> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public WorkingPlan create(WorkingPlanDto dto) {
        var newWorkingPlan = new WorkingPlan();
        newWorkingPlan = repository.save(newWorkingPlan);

        updateFields(dto, newWorkingPlan);

        return repository.save(newWorkingPlan);
    }

    private void updateFields(final WorkingPlanDto dto, final WorkingPlan newWorkingPlan) {
        setFieldIfRequested(dto::getCurriculumId, curriculumService::findById, newWorkingPlan::setCurriculum);
        setFieldIfRequested(dto::getScientificResearchSubjectId, curriculumSubjectService.subjectInfoService()::findById,
                newWorkingPlan::setScientificResearchSubject);
        setFieldIfRequested(dto::getStateCertificationId, certificationService::findById, newWorkingPlan::setStateCertification);
        setFieldIfRequested(dto::getPracticeId, practiceService::findById, newWorkingPlan::setPractice);
        setFieldIfRequested(dto::getGroupIds, academicGroupService::findAllByIds, this::listToSet, newWorkingPlan::setGroups);
        setFieldIfRequested(dto::getDiplomaPreparationIds, diplomaPreparationService::findAllByIds,
                this::listToSet, newWorkingPlan::setDiplomaPreparations);

        setFieldIfRequested(dto::getSubjectIdsByCiphers,
                (Map<String, Integer> ciphersIds) -> curriculumSubjectService.getCurriculumSubjects(ciphersIds, newWorkingPlan),
                identity(), newWorkingPlan::setCurriculumSubjects);
        ofNullable(dto.getDenotation()).ifPresent(newWorkingPlan::setDenotation);
    }

    public <T, I, R> void setFieldIfRequested(Supplier<T> nullable,
                                              Function<T, I> findFunction,
                                              Function<I, R> mapper,
                                              Consumer<R> setter) {
        ofNullable(nullable.get())
                .map(findFunction)
                .map(mapper)
                .ifPresent(setter);
    }

    public <T, R> void setFieldIfRequested(Supplier<T> nullable,
                                           Function<T, Optional<R>> findFunction,
                                           Consumer<R> setter) {
        ofNullable(nullable.get())
                .map(findFunction)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(setter);
    }

    private <T> Set<T> listToSet(List<T> list) {
        return new HashSet<>(list);
    }

    @Override
    public WorkingPlan update(Integer id, WorkingPlanDto dto) {
        WorkingPlan updated = repository.findById(id).orElseThrow();
        updateFields(dto, updated);
        return repository.save(updated);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
