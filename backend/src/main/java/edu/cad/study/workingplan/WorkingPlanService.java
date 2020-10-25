package edu.cad.study.workingplan;

import edu.cad.entities.WorkingPlan;
import edu.cad.study.DropdownOption;
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
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkingPlanService implements EntityService<WorkingPlan, Integer, WorkingPlanDto> {
    WorkingPlanRepositoryWrapper repository;
    CurriculumService curricula;
    StateCertificationService certifications;
    PracticeService practices;
    AcademicGroupService groups;
    DiplomaPreparationService diplomaPreparations;
    CurriculumSubjectService curriculumSubjects;

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

    private void updateFields(final WorkingPlanDto updates, final WorkingPlan workingPlan) {
        setFieldIfRequested(
                updates::getCurriculum,
                curricula::findById,
                workingPlan::setCurriculum
        );
        setFieldIfRequested(
                updates::getScientificResearchSubject,
                curriculumSubjects.subjectInfoService()::findById,
                workingPlan::setScientificResearchSubject
        );
        setFieldIfRequested(
                updates::getStateCertification,
                certifications::findById,
                workingPlan::setStateCertification
        );
        setFieldIfRequested(
                updates::getPractice,
                practices::findById,
                workingPlan::setPractice
        );
        setListFieldIfRequested(
                updates::getGroups,
                DropdownOption::id,
                groups::findAllByIds,
                groups -> workingPlan.setGroups(Set.copyOf(groups))
        );
        setListFieldIfRequested(
                updates::getDiplomaPreparations,
                DropdownOption::id,
                diplomaPreparations::findAllByIds,
                diplomaPreparations ->
                        workingPlan.setDiplomaPreparations(Set.copyOf(diplomaPreparations))
        );

        setListFieldIfRequested(
                updates::getSubjectsWithCiphers,
                identity(),
                (subjectsWithCiphers) ->
                        curriculumSubjects.getCurriculumSubjects(subjectsWithCiphers, workingPlan),
                (subjects) -> workingPlan.setCurriculumSubjects(Set.copyOf(subjects))
        );

        ofNullable(updates.getDenotation()).ifPresent(workingPlan::setDenotation);
    }

    public <T, I, S> void setListFieldIfRequested(Supplier<List<T>> nullable,
                                                  Function<T, I> idMapper,
                                                  Function<List<I>, Collection<S>> searchFunction,
                                                  Consumer<Collection<S>> setter) {
        List<I> ids = ofNullable(nullable.get())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(idMapper)
                .collect(toList());

        Collection<S> valuesToBeSet = searchFunction.apply(ids);
        setter.accept(valuesToBeSet);
    }

    public <R> void setFieldIfRequested(Supplier<DropdownOption> nullable,
                                        Function<Integer, Optional<R>> findFunction,
                                        Consumer<R> setter) {
        ofNullable(nullable.get())
                .map(DropdownOption::id)
                .map(findFunction)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(setter);
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
