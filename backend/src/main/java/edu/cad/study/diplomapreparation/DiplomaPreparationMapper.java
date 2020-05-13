package edu.cad.study.diplomapreparation;

import edu.cad.entities.DiplomaPreparation;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class DiplomaPreparationMapper implements EntityMapper<DiplomaPreparation, DiplomaPreparationDto> {
    @Override
    public DiplomaPreparationDto toResponse(DiplomaPreparation e) {
        return new DiplomaPreparationDto()
                .setId(e.getId())
                .setNorm(e.getNorm())
                .setDepartmentId(e.getDepartment().getId())
                .setWorkTypeId(e.getWorkType().getId())
                .setWorkingPlanId(e.getWorkingPlan().getId());
    }

    @Override
    public DropdownOption toOption(DiplomaPreparation e) {
        throw new UnsupportedOperationException("Unsupported");
    }
}
