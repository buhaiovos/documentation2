package edu.cad.study.load.other;

import edu.cad.study.load.other.distributed.DistributedOtherLoadDto;
import edu.cad.study.load.other.distributed.OtherLoadDistributionDto;

import java.util.List;

public interface OtherLoadDistributionService {
    List<DistributedOtherLoadDto> getAllByLoadId(int loadId);

    List<DistributedOtherLoadDto> getAllByEmployeeId(int employeeId);

    void deleteById(int distributionId);

    void submitDistribution(OtherLoadDistributionDto dto);

    double getAllDistributedHoursForLoadById(long id);
}
