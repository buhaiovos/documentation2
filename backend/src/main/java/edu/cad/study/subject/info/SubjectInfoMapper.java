package edu.cad.study.subject.info;

import edu.cad.entities.SubjectInfo;
import edu.cad.study.EntityMapper;
import edu.cad.study.control.ControlDto;
import edu.cad.study.control.ControlMapper;
import edu.cad.utils.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectInfoMapper implements EntityMapper<SubjectInfo, SubjectInfoDto> {
    private final ControlMapper controlMapper;

    @Override
    public SubjectInfoDto toResponse(SubjectInfo info) {
        return new SubjectInfoDto(
                info.getId(),
                info.getSemester(),
                info.getSemestersDuration(),
                info.getSubjectHeader().getId(),
                info.getLectures(),
                info.getActualLectures(),
                info.getLabs(),
                info.getActualLabs(),
                info.getPractices(),
                info.getActualPractices(),
                info.getEcts(),
                getControlsIds(info)
        );
    }

    private ControlDto[] getControlsIds(SubjectInfo info) {
        return info.getControls()
                .stream()
                .map(controlMapper::toResponse)
                .toArray(ControlDto[]::new);
    }

    @Override
    @Deprecated
    public Option toOption(SubjectInfo e) {
        return null;
    }
}
