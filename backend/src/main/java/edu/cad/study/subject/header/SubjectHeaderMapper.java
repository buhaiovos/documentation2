package edu.cad.study.subject.header;

import edu.cad.entities.SubjectHeader;
import edu.cad.entities.SubjectType;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

import static edu.cad.utils.Utils.nullOr;

@Component
public class SubjectHeaderMapper implements EntityMapper<SubjectHeader, SubjectHeaderDto> {
    @Override
    public SubjectHeaderDto toResponse(SubjectHeader e) {
        return new SubjectHeaderDto(
                e.getId(),
                e.getDenotation(),
                nullOr(e.getSuperSubject(), ss -> new DropdownOption(ss.getId(), ss.getDenotation())),
                nullOr(e.getCurriculumSection(), cs -> new DropdownOption(cs.getId(), cs.getDenotation())),
                nullOr(e.getWorkingPlanSection(), ws -> new DropdownOption(ws.getId(), ws.getDenotation())),
                nullOr(e.getType(), SubjectType::getId),
                nullOr(e.getDepartment(), d -> new DropdownOption(d.getId(), d.getDenotation()))
        );
    }

    @Override
    public DropdownOption toOption(SubjectHeader e) {
        return new DropdownOption(e.getId(), e.getDenotation());
    }
}
