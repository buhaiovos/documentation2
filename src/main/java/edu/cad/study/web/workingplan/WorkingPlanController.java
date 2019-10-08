package edu.cad.study.web.workingplan;

import edu.cad.study.service.WorkingPlanService;
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
@RequestMapping("/v2/working-plans")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkingPlanController extends ActionProcessor<WorkingPlanDto, WorkingPlanDto, Integer> {
    WorkingPlanService service;
    WorkingPlanMapper mapper;

    @Override
    public List<WorkingPlanDto> list() {
        return service.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @Override
    public WorkingPlanDto create(WorkingPlanDto request) {
        return null;
    }

    @Override
    public WorkingPlanDto update(WorkingPlanDto request) {
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
    public WorkingPlanDto getDependent(HttpServletRequest request) {
        return null;
    }
}
