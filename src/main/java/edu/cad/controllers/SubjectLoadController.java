package edu.cad.controllers;

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

    @GetMapping
    public List<String> getAll() {
        return null;
    }

    @PostMapping("/{id}")
    public ResponseEntity submitLoadDistribution(@PathVariable int id, @RequestBody SubjectLoadDistributionDto dto) {
        log.info("Accepted dto: {} with id: {}", dto, id);
        return ResponseEntity.ok().build();
    }
}
