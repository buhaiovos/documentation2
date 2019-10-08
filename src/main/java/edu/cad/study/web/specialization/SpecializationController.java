package edu.cad.study.web.specialization;

import edu.cad.study.service.SpecializationService;
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
@RequestMapping("/v2/specializations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecializationController extends ActionProcessor<SpecializationDto, SpecializationDto, Integer> {
    SpecializationService service;
    SpecializationMapper mapper;

    @Override
    public List<SpecializationDto> list() {
        return service.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @Override
    public SpecializationDto create(SpecializationDto request) {
        return null;
    }

    @Override
    public SpecializationDto update(SpecializationDto request) {
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
    public SpecializationDto getDependent(HttpServletRequest request) {
        return null;
    }
}
