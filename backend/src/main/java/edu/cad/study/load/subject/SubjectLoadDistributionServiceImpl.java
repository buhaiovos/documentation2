package edu.cad.study.load.subject;

import edu.cad.entities.DistributedSubjectStudyLoad;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.services.StaffService;
import edu.cad.study.load.subject.distributed.DistributedSubjectLoadDto;
import edu.cad.study.load.subject.distributed.DistributedSubjectStudyLoadRepositoryWrapper;
import edu.cad.study.load.subject.distributed.SubjectLoadDistributionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectLoadDistributionServiceImpl implements SubjectLoadDistributionService {

    private final DistributedSubjectStudyLoadRepositoryWrapper submittedLoadDao;
    private final SubjectStudyLoadRepositoryWrapper studyLoadDao;
    private final StaffService staffService;

    @Override
    public List<DistributedSubjectLoadDto> getAllByLoadId(int loadId) {
        return submittedLoadDao.findAll()
                .stream()
                .filter(load -> load.getTargetLoad().getId() == loadId)
                .map(this::toDto)
                .collect(toList());
    }

    private DistributedSubjectLoadDto toDto(DistributedSubjectStudyLoad loadSubmitted) {
        var distributedSubjectLoadDto = new DistributedSubjectLoadDto();
        distributedSubjectLoadDto.setId(loadSubmitted.getId());
        distributedSubjectLoadDto.setAmount(loadSubmitted.getAmount());
        distributedSubjectLoadDto.setEmployeeName(loadSubmitted.getAssignedProfessor().getFullName());
        distributedSubjectLoadDto.setSubjectLoadId(loadSubmitted.getTargetLoad().getId());
        distributedSubjectLoadDto.setType(loadSubmitted.getStudyLoadType().name());
        return distributedSubjectLoadDto;
    }

    @Override
    public List<DistributedSubjectLoadDto> getAllByEmployeeId(int employeeId) {
        log.info("get all by employee id: {}", employeeId);

        return submittedLoadDao.findAll()
                .stream()
                .filter(load -> load.getAssignedProfessor().getId() == employeeId)
                .map(this::toDto)
                .collect(toList());
    }

    @Override
    public void deleteById(int distributionId) {
        log.info("deleting distribution by id: {}", distributionId);
        submittedLoadDao.deleteById(distributionId);
    }

    @Override
    public void submitDistribution(SubjectLoadDistributionDto dto) {
        log.info("submitting load for data: {}", dto);

        SubjectStudyLoad studyLoad = studyLoadDao.findById(dto.getLoadId())
                .orElseThrow(() -> new IllegalArgumentException(format("No load for id %d was found", dto.getLoadId())));

        DistributedSubjectStudyLoad distributedLoad = toDistributedLoad(dto, studyLoad);
        submittedLoadDao.save(distributedLoad);
    }

    private DistributedSubjectStudyLoad toDistributedLoad(SubjectLoadDistributionDto dto, SubjectStudyLoad studyLoad) {
        var distributedLoad = new DistributedSubjectStudyLoad();
        distributedLoad.setAmount(dto.getValue());
        distributedLoad.setTargetLoad(studyLoad);
        distributedLoad.setAssignedProfessor(staffService.getById(dto.getStaffId()));
        distributedLoad.setStudyLoadType(dto.getType());

        return distributedLoad;
    }
}
