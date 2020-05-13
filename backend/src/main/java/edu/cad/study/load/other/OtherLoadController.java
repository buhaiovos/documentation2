package edu.cad.study.load.other;

import edu.cad.domain.FormOfEducation;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/other-load")
@RequiredArgsConstructor
public class OtherLoadController {
    private final OtherLoadInfoService loadService;
    private final OtherLoadInfoTransformer transformer;

    @GetMapping
    public List<OtherLoadInfoDto> getOtherLoadInfo(@RequestParam("semester") @Positive int semester,
                                                   @RequestParam("education_form") FormOfEducation formOfEducation,
                                                   @RequestParam("financing") SourceOfFinancing sourceOfFinancing) {
        return loadService.search(semester, formOfEducation, sourceOfFinancing)
                .stream()
                .map(transformer::toDto)
                .collect(Collectors.toList());
    }
}
