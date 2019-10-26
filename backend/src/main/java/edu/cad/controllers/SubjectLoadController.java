package edu.cad.controllers;

import edu.cad.controllers.dto.SubjectLoadDto;
import edu.cad.controllers.dto.SubjectStudyLoadTransformer;
import edu.cad.domain.FormOfEducation;
import edu.cad.services.SubjectStudyLoadService;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subject-load")
@Slf4j
@RequiredArgsConstructor
@Validated
public class SubjectLoadController {
    private final SubjectStudyLoadService subjectLoadService;
    private final SubjectStudyLoadTransformer transformer;

    @GetMapping
    public List<SubjectLoadDto> getAllSubjectLoad(@RequestParam("semester") @Positive int semester,
                                                  @RequestParam("education_form") @NotNull FormOfEducation formOfEducation,
                                                  @RequestParam("financing") @NotNull SourceOfFinancing sourceOfFinancing) {
        return subjectLoadService.search(semester, formOfEducation, sourceOfFinancing)
                .stream()
                .map(transformer::toDto)
                .collect(Collectors.toList());
    }
}
