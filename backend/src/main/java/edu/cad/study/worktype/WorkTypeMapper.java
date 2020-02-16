package edu.cad.study.worktype;

import edu.cad.entities.WorkType;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
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
    public Option toOption(WorkType e) {
        return null;
    }
}
