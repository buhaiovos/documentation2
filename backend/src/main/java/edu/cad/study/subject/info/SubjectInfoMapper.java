package edu.cad.study.subject.info;

import edu.cad.entities.SubjectInfo;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import edu.cad.study.control.ControlDto;
import edu.cad.study.control.ControlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
    public DropdownOption toOption(SubjectInfo e) {
        throw new UnsupportedOperationException("Unsupported");
    }

    public List<RichSubjectInfoDto> toEnrichedResponse(List<SubjectInfo> infos) {
        return infos.stream()
                .map(si -> new RichSubjectInfoDto(
                        si.getId(),
                        si.getSubjectHeader().getDenotation(),
                        si.getSemester(),
                        si.getSemestersDuration(),
                        si.getLectures(),
                        si.getActualLectures(),
                        si.getLabs(),
                        si.getActualLabs(),
                        si.getPractices(),
                        si.getActualPractices(),
                        si.getEcts(),
                        getControlsIds(si)
                ))
                .collect(toList());
    }
}
