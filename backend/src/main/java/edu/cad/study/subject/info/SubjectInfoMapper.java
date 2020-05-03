package edu.cad.study.subject.info;

import edu.cad.entities.Control;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

@Component
public class SubjectInfoMapper implements EntityMapper<SubjectInfo, SubjectInfoDto> {
    @Override
    public SubjectInfoDto toResponse(SubjectInfo e) {
        return new SubjectInfoDto(
                e.getId(),
                e.getSemester(),
                e.getSemestersDuration(),
                e.getLectures(),
                e.getActualLectures(),
                e.getLabs(),
                e.getActualLabs(),
                e.getPractices(),
                e.getActualPractices(),
                e.getEcts(),
                e.getSubjectHeader().getId(),
                getControlsIds(e)
        );
    }

    private int[] getControlsIds(SubjectInfo e) {
        return e.getControls().stream()
                .mapToInt(Control::getId)
                .toArray();
    }

    @Override
    public Option toOption(SubjectInfo e) {
        return null;
    }
}
