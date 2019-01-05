package edu.cad.controllers;

import edu.cad.controllers.dto.DistributedSubjectLoadDto;
import edu.cad.controllers.dto.SubjectLoadDistributionDto;
import edu.cad.services.load.distribution.SubjectLoadDistributionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject-load")
@RequiredArgsConstructor
@Slf4j
public class SubjectLoadController {
    private final SubjectLoadDistributionService distributionService;

    @GetMapping(params = "employee_id")
    public List<DistributedSubjectLoadDto> getAllByEmployeeId(@RequestParam("employee_id") int employeeId) {
        return distributionService.getAllByEmployeeId(employeeId);
    }

    @GetMapping("/{load-id}")
    public List<DistributedSubjectLoadDto> getDistributedLoadByLoadId(@PathVariable("load-id") int loadId) {
        return distributionService.getAllByLoadId(loadId);
    }

    @PostMapping("/{load-id}")
    public ResponseEntity submitLoadDistribution(@PathVariable("load-id") int loadId, @RequestBody SubjectLoadDistributionDto dto) {
        log.info("Accepted dto: {} with id: {}", dto, loadId);
        distributionService.submitDistribution(loadId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteLoadDistribution(@RequestParam("distribution_id") int distributionId) {
        distributionService.deleteById(distributionId);
        return ResponseEntity.ok().build();
    }
}
