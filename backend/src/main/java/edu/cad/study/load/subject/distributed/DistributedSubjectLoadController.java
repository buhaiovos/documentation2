package edu.cad.study.load.subject.distributed;

import edu.cad.study.load.subject.SubjectLoadDistributionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/distributed-subject-load")
@RequiredArgsConstructor
@Slf4j
public class DistributedSubjectLoadController {
    private final SubjectLoadDistributionService distributionService;

    @GetMapping(params = "employee_id")
    public List<DistributedSubjectLoadDto> getAllByEmployeeId(@RequestParam("employee_id") int employeeId) {
        return distributionService.getAllByEmployeeId(employeeId);
    }

    @GetMapping(params = "load_id")
    public List<DistributedSubjectLoadDto> getDistributedLoadByLoadId(@RequestParam("load_id") int loadId) {
        return distributionService.getAllByLoadId(loadId);
    }

    @PostMapping
    public ResponseEntity submitLoadDistribution(@RequestBody SubjectLoadDistributionDto dto) {
        log.info("Accepted dto: {}", dto);
        distributionService.submitDistribution(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLoadDistribution(@PathVariable int id) {
        distributionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
