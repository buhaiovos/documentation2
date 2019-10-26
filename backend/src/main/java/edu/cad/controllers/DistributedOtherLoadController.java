package edu.cad.controllers;

import edu.cad.controllers.dto.DistributedOtherLoadDto;
import edu.cad.controllers.dto.OtherLoadDistributionDto;
import edu.cad.services.load.distribution.OtherLoadDistributionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/distributed-other-load")
@RequiredArgsConstructor
@Slf4j
public class DistributedOtherLoadController {
    private final OtherLoadDistributionService distributionService;

    @GetMapping(params = "employee_id")
    public List<DistributedOtherLoadDto> getAllByEmployeeId(@RequestParam("employee_id") int employeeId) {
        throw new IllegalArgumentException("blah");
    }

    @GetMapping(params = "load_id")
    public List<DistributedOtherLoadDto> getDistributedLoadByLoadId(@RequestParam("load_id") int loadId) {
        return distributionService.getAllByLoadId(loadId);
    }

    @PostMapping
    public ResponseEntity submitLoadDistribution(@RequestBody OtherLoadDistributionDto dto) {
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
