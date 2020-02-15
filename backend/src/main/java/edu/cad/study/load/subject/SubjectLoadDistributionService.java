package edu.cad.study.load.subject;

import edu.cad.study.load.subject.distributed.DistributedSubjectLoadDto;
import edu.cad.study.load.subject.distributed.SubjectLoadDistributionDto;

import java.util.List;

public interface SubjectLoadDistributionService {
    List<DistributedSubjectLoadDto> getAllByLoadId(int loadId);

    List<DistributedSubjectLoadDto> getAllByEmployeeId(int employeeId);

    void deleteById(int distributionId);

    void submitDistribution(SubjectLoadDistributionDto dto);
}
