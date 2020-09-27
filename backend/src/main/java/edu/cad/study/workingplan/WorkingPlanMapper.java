package edu.cad.study.workingplan;

import edu.cad.entities.*;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import edu.cad.study.curriculum.CurriculumMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
class WorkingPlanMapper implements EntityMapper<WorkingPlan, WorkingPlanDto> {
    @Override
    public WorkingPlanDto toResponse(WorkingPlan wp) {
        var dto = new WorkingPlanDto();
        dto.setId(wp.getId())
                .setDenotation(wp.getDenotation())
                .setSubjectsWithCiphers(CurriculumMapper.getSubjectsWithCiphers(wp));
        dto.setCurriculum(using(wp.getCurriculum(), Curriculum::getId, Curriculum::getDenotation))
                .setStateCertification(using(wp.getStateCertification(), StateCertification::getId, StateCertification::toString))
                .setPractice(using(wp.getPractice(), Practice::getId, Practice::toString))
                .setScientificResearchSubject(
                        using(
                                wp.getScientificResearchSubject(),
                                SubjectInfo::getId,
                                subjectInfo -> subjectInfo.getSubjectHeader().getDenotation()
                        )
                )
                .setDiplomaPreparations(
                        wp.getDiplomaPreparations()
                                .stream()
                                .map(dp -> new DropdownOption(dp.getId(), dp.toString())).collect(toList())
                )
                .setGroups(
                        wp.getGroups()
                                .stream()
                                .map(group -> new DropdownOption(group.getId(), group.getCipher()))
                                .collect(toList())

                );
        return dto;
    }

    public <T> DropdownOption using(T target, Function<T, Integer> id, Function<T, String> denotation) {
        return target != null
                ? new DropdownOption(id.apply(target), denotation.apply(target))
                : DropdownOption.empty();
    }

    public DropdownOption toOption(WorkingPlan wp) {
        return new DropdownOption(wp.getId(), wp.getDenotation());
    }
}
