package edu.cad.study.diplomapreparation;

import edu.cad.entities.DiplomaPreparation;
import edu.cad.study.DropdownOption;
import edu.cad.study.EntityMapper;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class DiplomaPreparationMapper implements EntityMapper<DiplomaPreparation, DiplomaPreparationDto> {
    @Override
    public DiplomaPreparationDto toResponse(DiplomaPreparation e) {
        return new DiplomaPreparationDto()
                .setId(e.getId())
                .setNorm(e.getNorm())
                .setDepartment(ofNullable(e.getDepartment())
                        .map(d -> new DropdownOption(d.getId(), d.getDenotation()))
                        .orElse(DropdownOption.empty()))
                .setWorkType(ofNullable(e.getWorkType())
                        .map(wt -> new DropdownOption(wt.getId(), wt.getDenotation()))
                        .orElse(DropdownOption.empty()));
    }

    @Override
    public DropdownOption toOption(DiplomaPreparation e) {
        throw new UnsupportedOperationException("Unsupported");
    }
}
