package edu.cad.services.load.distribution;

import edu.cad.controllers.dto.DistributedOtherLoadDto;
import edu.cad.controllers.dto.DistributedSubjectLoadDto;
import edu.cad.controllers.dto.OtherLoadDistributionDto;
import edu.cad.controllers.dto.SubjectLoadDistributionDto;

import java.util.List;

public interface OtherLoadDistributionService {
    List<DistributedOtherLoadDto> getAllByLoadId(int loadId);

    List<DistributedOtherLoadDto> getAllByEmployeeId(int employeeId);

    void deleteById(int distributionId);

    void submitDistribution(OtherLoadDistributionDto dto);
}
