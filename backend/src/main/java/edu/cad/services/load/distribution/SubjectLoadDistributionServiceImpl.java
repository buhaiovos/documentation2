package edu.cad.services.load.distribution;

import edu.cad.controllers.dto.DistributedSubjectLoadDto;
import edu.cad.controllers.dto.SubjectLoadDistributionDto;
import edu.cad.daos.HibernateDao;
import edu.cad.entities.DistributedSubjectStudyLoad;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.services.StaffService;
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

    private final HibernateDao<DistributedSubjectStudyLoad> submittedLoadDao;
    private final HibernateDao<SubjectStudyLoad> studyLoadDao;
    private final StaffService staffService;

    @Override
    public List<DistributedSubjectLoadDto> getAllByLoadId(int loadId) {
        return submittedLoadDao.getAll()
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

        return submittedLoadDao.getAll()
                .stream()
                .filter(load -> load.getAssignedProfessor().getId() == employeeId)
                .map(this::toDto)
                .collect(toList());
    }

    @Override
    public void deleteById(int distributionId) {
        log.info("deleting distribution by id: {}", distributionId);
        submittedLoadDao.delete(distributionId);
    }

    @Override
    public void submitDistribution(SubjectLoadDistributionDto dto) {
        log.info("submitting load for data: {}", dto);

        SubjectStudyLoad studyLoad = studyLoadDao.getById(dto.getLoadId())
                .orElseThrow(() -> new IllegalArgumentException(format("No load for id %d was found", dto.getLoadId())));

        DistributedSubjectStudyLoad distributedLoad = toDistributedLoad(dto, studyLoad);
        submittedLoadDao.create(distributedLoad);
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
