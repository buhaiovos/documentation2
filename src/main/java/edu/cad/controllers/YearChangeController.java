package edu.cad.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.cad.services.years.DbYearsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/years")
public class YearChangeController {
    private final DbYearsService dbYearsService;

    public YearChangeController(DbYearsService dbYearsService) {
        this.dbYearsService = dbYearsService;
    }

    @GetMapping("/all")
    public List<Integer> getAllYears() {
        final Set<Integer> allYears = dbYearsService.getAll();
        return listOfYearsPlusOne(allYears);
    }

    private List<Integer> listOfYearsPlusOne(Set<Integer> allYears) {
        final ArrayList<Integer> existingYears = new ArrayList<>(allYears);
        final int greatestYear = existingYears.get(existingYears.size() - 1);
        existingYears.add(greatestYear + 1);
        return existingYears;
    }

    @GetMapping("/current")
    public YearResponseDto getCurrentYear() {
        final int currentDbYear = dbYearsService.getCurrent();
        return new YearResponseDto(currentDbYear);
    }

    @PostMapping
    public void switchYear(@RequestParam("selected_year") int year) {
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