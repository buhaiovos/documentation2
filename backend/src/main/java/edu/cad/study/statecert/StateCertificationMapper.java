package edu.cad.study.statecert;

import edu.cad.entities.StateCertification;
import edu.cad.study.EntityMapper;
import edu.cad.utils.Option;
import org.springframework.stereotype.Component;

@Component
public class StateCertificationMapper implements EntityMapper<StateCertification, StateCertificationDto> {
    @Override
    public StateCertificationDto toResponse(StateCertification e) {
        return new StateCertificationDto()
                .setId(e.getId())
                .setForm(e.getForm())
                .setSemester(e.getSemester())
                .setStart(e.getStart())
                .setFinish(e.getFinish());
    }

    @Override
    public Option toOption(StateCertification e) {
        return null;
    }
}
