package edu.cad.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.cad.services.years.DbYearsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/years")
public class YearChangeController {
    private final DbYearsService dbYearsService;

    public YearChangeController(DbYearsService dbYearsService) {
        this.dbYearsService = dbYearsService;
    }

    @GetMapping("/all")
    public Set<Integer> getAllYears() {
        return dbYearsService.getAll();
    }

    @GetMapping("/current")
    public YearResponseDto getCurrentYear() {
        final int currentDbYear = dbYearsService.getCurrent();
        return new YearResponseDto(currentDbYear);
    }

    @PostMapping
    public void switchYear(@RequestParam("selected-year") int year) {
        dbYearsService.switchToYear(year);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class YearResponseDto {
        @JsonProperty("current_year")
        private int year;
    }
}