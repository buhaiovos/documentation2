package edu.cad.study;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@RequiredArgsConstructor
@Slf4j
public abstract class ActionController<Entity, Id, Dto> extends ActionProcessor<Dto, Dto, Id> {
    private final EntityService<Entity, Id, Dto> service;
    private final EntityMapper<Entity, Dto> mapper;

    private String entityName;

    @Override
    public List<Dto> list() {
        List<Entity> groups = service.getAll();
        log.info("Getting list of {}, size: {}", getEntityName(), groups.size());
        return toResponse(groups);
    }

    @Override
    public Dto create(Dto request) {
        log.info("Creating {}: {}", getEntityName(), request);
        Entity entity = service.create(request);
        return mapper.toResponse(entity);
    }

    @Override
    public Optional<Dto> getById(Id id) {
        log.info("Getting {} by id: {}", getEntityName(), id);
        return service.findById(id)
                .map(mapper::toResponse);
    }

    @Override
    public Dto update(Id id, Dto request) {
        log.info("Updating {}: {}", getEntityName(), request);
        Entity updatedEntity = service.update(id, request);
        return mapper.toResponse(updatedEntity);
    }

    @Override
    public void delete(Id id) {
        log.info("Deleting {} by id: {}", getEntityName(), id);
        service.deleteById(id);
    }

    @Override
    public List<DropdownOption> getOptions() {
        log.info("Getting options ({})", getEntityName());
        return service.getAll()
                .stream()
                .map(mapper::toOption)
                .collect(toList());
    }

    private List<Dto> toResponse(List<Entity> groups) {
        return groups
                .stream()
                .map(mapper::toResponse)
                .collect(toList());
    }

    private String getEntityName() {
        if (entityName == null) {
            entityName = service.getClass()
                    .getSimpleName()
                    .replace("Service", EMPTY);
        }
        return entityName;
    }
}
