package edu.cad.study;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface EntityService<Entity, Id, Dto> {
    List<Entity> getAll();

    Optional<Entity> findById(Id id);

    Entity create(Dto academicGroup);

    Entity update(Id id, Dto updatedGroup);

    void deleteById(Id id);
}
