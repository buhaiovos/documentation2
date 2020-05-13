package edu.cad.study.worktype;

import edu.cad.entities.WorkType;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class WorkTypeMapper implements EntityMapper<WorkType, WorkTypeDto> {
    @Override
    public WorkTypeDto toResponse(WorkType e) {
        return new WorkTypeDto()
                .setId(e.getId())
                .setDenotation(e.getDenotation());
    }

    @Override
    public DropdownOption toOption(WorkType e) {
        throw new UnsupportedOperationException("Unsupported");
    }
}
