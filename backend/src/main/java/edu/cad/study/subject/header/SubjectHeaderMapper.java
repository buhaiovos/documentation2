package edu.cad.study.subject.header;

import edu.cad.entities.Section;
import edu.cad.entities.SubjectHeader;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

import static edu.cad.utils.Utils.nullOr;

@Component
public class SubjectHeaderMapper implements EntityMapper<SubjectHeader, SubjectHeaderDto> {
    @Override
    public SubjectHeaderDto toResponse(SubjectHeader e) {
        return new SubjectHeaderDto()
                .setId(e.getId())
                .setDenotation(e.getDenotation())
                .setDepartmentId(e.getDepartment().getId())
                .setSubjectTypeId(e.getType().getId())
                .setSuperSubjectId(nullOr(e.getSuperSubject(), SubjectHeader::getId))
                .setCurriculumSectionId(nullOr(e.getCurriculumSection(), Section::getId))
                .setWorkingPlanSectionId(nullOr(e.getWorkingPlanSection(), Section::getId));
    }

    @Override
    public Option toOption(SubjectHeader e) {
        return null;
    }
}
