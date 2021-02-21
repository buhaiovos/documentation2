package edu.cad.year;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/years")
class YearsController {
    private final YearsOfInformationConfigurationProperties years;
    private final YearProviderImpl yearProvider;

    @GetMapping
    public List<Integer> getAll() {
        return years.getSorted().stream().map(Integer::valueOf).collect(toList());
    }

    @PostMapping("/current")
    public YearDto switchYear(@RequestBody YearDto year) {
        yearProvider.setCurrentYear(year.year);
        return year;
    }

    @GetMapping("/current")
    public YearDto getCurrent() {
        return new YearDto(yearProvider.getCurrent());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class YearDto {
        private int year;
    }
}
