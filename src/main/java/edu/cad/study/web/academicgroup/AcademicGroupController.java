package edu.cad.study.web.academicgroup;

import edu.cad.entities.AcademicGroup;
import edu.cad.study.service.AcademicGroupService;
import edu.cad.study.web.ActionProcessor;
import edu.cad.utils.gson.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/v2/academic-groups")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AcademicGroupController extends ActionProcessor<AcademicGroupDto, AcademicGroupDto, Integer> {
    AcademicGroupService service;
    AcademicGroupMapper mapper;

    @Override
    public List<AcademicGroupDto> list() {
        List<AcademicGroup> groups = service.getAll();
        log.info("getting list of academic groups, size: {}", groups.size());
        return toResponse(groups);
    }

    @Override
    public AcademicGroupDto create(AcademicGroupDto request) {
        return null;
    }

    @Override
    public AcademicGroupDto update(AcademicGroupDto request) {
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
    public AcademicGroupDto getDependent(HttpServletRequest request) {
        return null;
    }

    private List<AcademicGroupDto> toResponse(List<AcademicGroup> groups) {
        return groups
                .stream()
                .map(mapper::toResponse)
                .collect(toList());
    }
}
