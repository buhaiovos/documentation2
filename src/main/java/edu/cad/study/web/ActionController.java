package edu.cad.study.web;

import edu.cad.study.service.EntityService;
import edu.cad.utils.gson.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public abstract class ActionController<Entity, Id, Dto> extends ActionProcessor<Dto, Dto, Id> {
    EntityService<Entity, Id, Dto> service;
    EntityMapper<Entity, Dto> mapper;

    @Override
    public List<Dto> list() {
        List<Entity> groups = service.getAll();
        log.info("getting list of academic groups, size: {}", groups.size());
        return toResponse(groups);
    }

    @Override
    public Dto create(Dto request) {
        log.info("creating new academic group: {}", request);
        Entity academicGroup = service.create(request);
        return mapper.toResponse(academicGroup);
    }

    @Override
    public Dto update(Dto request) {
        log.info("updating existing academic group {}", request);
        Entity updatedGroup = service.update(request);
        return mapper.toResponse(updatedGroup);
    }

    @Override
    public void delete(Id id) {
        log.info("deleting academic group with id: {}", id);
        service.deleteById(id);
    }

    @Override
    public List<Option> getOptions() {
        return service.getAll()
                .stream()
                .map(mapper::toOption)
                .collect(toList());
    }

    @Override
    public abstract List<Dto> getDependent(HttpServletRequest request);

    private List<Dto> toResponse(List<Entity> groups) {
        return groups
                .stream()
                .map(mapper::toResponse)
                .collect(toList());
    }
}
