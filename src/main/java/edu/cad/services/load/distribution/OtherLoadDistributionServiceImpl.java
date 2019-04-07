package edu.cad.services.load.distribution;

import edu.cad.controllers.dto.DistributedOtherLoadDto;
import edu.cad.controllers.dto.OtherLoadDistributionDto;
import edu.cad.daos.HibernateDao;
import edu.cad.daos.OtherLoadInfoDao;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.entities.DistributedOtherLoadInfo;
import edu.cad.services.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtherLoadDistributionServiceImpl implements OtherLoadDistributionService {
    private final OtherLoadInfoDao otherLoadInfoDao;
    private final HibernateDao<DistributedOtherLoadInfo> otherLoadInfoSubmittedDao;
    private final StaffService staffService;

    @Override
    public List<DistributedOtherLoadDto> getAllByLoadId(int loadId) {
        log.info("getting all distribution info for load with id: '{}'", loadId);

        return otherLoadInfoSubmittedDao.getAll()
                .stream()
                .filter(load -> load.getId() == loadId)
                .map(this::toDto)
                .collect(toList());
    }

    private DistributedOtherLoadDto toDto(DistributedOtherLoadInfo distributedLoad) {
        var dto = new DistributedOtherLoadDto();
        dto.setEmployeeName(distributedLoad.getAssignedProfessor().getFullName());
        dto.setId(distributedLoad.getId());
        dto.setOtherLoadId(distributedLoad.getTargetLoad().getId());
        dto.setValue(distributedLoad.getAmount());

        return dto;
    }

    @Override
    public List<DistributedOtherLoadDto> getAllByEmployeeId(int employeeId) {
        log.info("getting all distribution info by employee id '{}'", employeeId);

        return otherLoadInfoSubmittedDao.getAll()
                .stream()
                .filter(load -> load.getAssignedProfessor().getId() == employeeId)
                .map(this::toDto)
                .collect(toList());
    }

    @Override
    public void deleteById(int distributionId) {
        log.info("deleting distribution info with id: '{}'", distributionId);
        otherLoadInfoSubmittedDao.delete(distributionId);
    }

    @Override
    public void submitDistribution(OtherLoadDistributionDto dto) {
        log.info("submitting distribution info with data: {}", dto);

        OtherLoadInfo otherLoadInfo = otherLoadInfoDao.getById(dto.getOtherLoadId())
                .orElseThrow();
        DistributedOtherLoadInfo distributed = createDistributedOtherLoadInfo(dto, otherLoadInfo);
        otherLoadInfoSubmittedDao.create(distributed);

        log.info("created new distribution info with id: {}", distributed.getId());
    }

    @Override
    public double getAllDistributedHoursForLoadById(long id) {
        return otherLoadInfoSubmittedDao.getAll()
                .stream()
                .filter(load -> load.getTargetLoad().getId() == id)
                .mapToDouble(DistributedOtherLoadInfo::getAmount)
                .sum();
    }

    private DistributedOtherLoadInfo createDistributedOtherLoadInfo(OtherLoadDistributionDto dto, OtherLoadInfo otherLoadInfo) {
        var otherLoadDistributed = new DistributedOtherLoadInfo();
        otherLoadDistributed.setAmount(dto.getValue());
        otherLoadDistributed.setTargetLoad(otherLoadInfo);
        otherLoadDistributed.setAssignedProfessor(staffService.getById(dto.getStaffId()));

        return otherLoadDistributed;
    }
}
