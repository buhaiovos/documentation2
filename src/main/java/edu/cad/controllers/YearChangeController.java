package edu.cad.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.cad.utils.databaseutils.DatabaseSwitcher;
import edu.cad.utils.databaseutils.DatabaseYears;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/years")
public class YearChangeController {
    private final DatabaseSwitcher databaseSwitcher;
    private final ServletContext servletContext;

    public YearChangeController(DatabaseSwitcher databaseSwitcher, ServletContext servletContext) {
        this.databaseSwitcher = databaseSwitcher;
        this.servletContext = servletContext;
    }

    @GetMapping("/all")
    public List<Integer> getAllYears() {
        return returnList();
    }

    @GetMapping("/current")
    public YearResponseDto getCurrentYear() {
        final int currentDbYear = databaseSwitcher.getDatabaseYear();
        return new YearResponseDto(currentDbYear);
    }

    @PostMapping
    public void switchYear(@RequestParam("selected-year") int selectedYear) {
        switchDB(selectedYear);
    }

    private List<Integer> returnList() {
//        String filePathOnServer = servletContext.getRealPath("DatabaseYears.txt");
//        DatabaseYears.setYearsFilePath(filePathOnServer);

        Set<Integer> yearsAvailable = DatabaseYears.getAllYears();
        ArrayList<Integer> years = new ArrayList<>(yearsAvailable);
        years.add(years.get(years.size() - 1) + 1);
        years.trimToSize();

        return years.stream().sorted().collect(Collectors.toList());
    }

    private void switchDB(int selectedYear) {
        databaseSwitcher.switchDatabase(selectedYear);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class YearResponseDto {
        @JsonProperty("current_year")
        private int year;
    }
}