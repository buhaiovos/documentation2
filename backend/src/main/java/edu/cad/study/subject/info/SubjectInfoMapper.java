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
        return new SubjectInfoDto()
                .setId(e.getId())
                .setActualLabs(e.getActualLabs())
                .setActualLectures(e.getActualLectures())
                .setActualPractices(e.getActualPractices())
                .setEcts(e.getEcts())
                .setLabs(e.getLabs())
                .setLectures(e.getLectures())
                .setPractices(e.getPractices())
                .setSemester(e.getSemester())
                .setSemestersDuration(e.getSemestersDuration())
                .setSubjectHeaderId(e.getSubjectHeader().getId())
                .setControlsIds(
                        e.getControls().stream()
                                .mapToInt(Control::getId)
                                .toArray()
                );
    }

    @Override
    public Option toOption(SubjectInfo e) {
        return null;
    }
}
