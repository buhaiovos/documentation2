package edu.cad.services.load.distribution;

import edu.cad.controllers.dto.DistributedSubjectLoadDto;
import edu.cad.controllers.dto.SubjectLoadDistributionDto;

import java.util.List;

public interface SubjectLoadDistributionService {
    List<DistributedSubjectLoadDto> getAllByLoadId(int loadId);

    List<DistributedSubjectLoadDto> getAllByEmployeeId(int employeeId);

    void deleteById(int distributionId);

    void submitDistribution(int loadId, SubjectLoadDistributionDto dto);
}
