package edu.cad.services.load.distribution;

import edu.cad.controllers.dto.DistributedSubjectLoadDto;
import edu.cad.controllers.dto.SubjectLoadDistributionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SubjectLoadDistributionServiceImpl implements SubjectLoadDistributionService {
    @Override
    public List<DistributedSubjectLoadDto> getAllByLoadId(int loadId) {
        log.info("get all by load id {}", loadId);
        return Collections.emptyList();
    }

    @Override
    public List<DistributedSubjectLoadDto> getAllByEmployeeId(int employeeId) {
        log.info("get all by employee id: {}", employeeId);
        return Collections.emptyList();
    }

    @Override
    public void deleteById(int distributionId) {
        log.info("deleting distribution by id: {}", distributionId);
    }

    @Override
    public void submitDistribution(int loadId, SubjectLoadDistributionDto dto) {
        log.info("submitting load for load with id: {} and data: {}", loadId, dto);
    }
}
