package edu.cad.study.web.educationform;

import edu.cad.study.service.EducationFormService;
import edu.cad.study.web.ActionProcessor;
import edu.cad.utils.gson.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/v2/education-forms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EducationFormController extends ActionProcessor<EducationFormDto, EducationFormDto, Integer> {
    EducationFormService service;
    EducationFormMapper mapper;

    @Override
    public List<EducationFormDto> list() {
        return service.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @Override
    public EducationFormDto create(EducationFormDto request) {
        return null;
    }

    @Override
    public EducationFormDto update(EducationFormDto request) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Option> getOptions() {
        return service.getAll()
                .stream()
                .map(mapper::toOption)
                .collect(toList());
    }

    @Override
    public EducationFormDto getDependent(HttpServletRequest request) {
        return null;
    }
}
