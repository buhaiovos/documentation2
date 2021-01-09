package edu.cad.study.workingplan;

import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.DiplomaPreparation;
import edu.cad.entities.WorkingPlan;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityService;
import edu.cad.study.curriculum.CurriculumService;
import edu.cad.study.curriculum.CurriculumSubjectService;
import edu.cad.study.diplomapreparation.DiplomaPreparationService;
import edu.cad.study.practice.PracticeService;
import edu.cad.study.statecert.StateCertificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        setFieldIfRequestedClearingOtherwise(
                updates::getScientificResearchSubject,
                curriculumSubjects.subjectInfoService()::findById,
                workingPlan::setScientificResearchSubject,
                () -> workingPlan.setScientificResearchSubject(null)
        );
        setFieldIfRequestedClearingOtherwise(
                updates::getStateCertification,
                certifications::findById,
                workingPlan::setStateCertification,
                () -> workingPlan.setStateCertification(null)
        );
        setFieldIfRequestedClearingOtherwise(
                updates::getPractice,
                practices::findById,
                workingPlan::setPractice,
                () -> workingPlan.setPractice(null)
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
        setFieldIfRequestedClearingOtherwise(
                nullable,
                findFunction,
                setter,
                () -> {}
        );
    }

    public <R> void setFieldIfRequestedClearingOtherwise(Supplier<DropdownOption> nullable,
                                                         Function<Integer, Optional<R>> findFunction,
                                                         Consumer<R> setter, Runnable clearingAction) {
        ofNullable(nullable.get())
                .map(DropdownOption::id)
                .map(findFunction)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresentOrElse(
                        setter,
                        clearingAction
                );
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

    @Transactional
    public WorkingPlan addDiplomaPreparation(Integer workingPlanId,
                                             DropdownOption newDiplomaPreparation) {
        WorkingPlan wp = repository.findById(workingPlanId).orElseThrow();
        DiplomaPreparation prep = diplomaPreparations.findById(newDiplomaPreparation.id()).orElseThrow();

        wp.getDiplomaPreparations().add(prep);
        prep.setWorkingPlan(wp);

        return repository.save(wp);
    }

    @Transactional(readOnly = true)
    public List<DropdownOption> getAllWorkingPlanSubjects(Integer workingPlanId) {
        return repository.findById(workingPlanId)
                .stream()
                .map(WorkingPlan::getCurriculumSubjects)
                .flatMap(Set::stream)
                .map(CurriculumSubject::getSubjectInfo)
                .map(info -> new DropdownOption(info.getId(), info.getSubjectHeader().getDenotation()))
                .collect(toList());
    }
}
