package edu.cad.study;

import java.util.List;
import java.util.Optional;

public interface EntityService<Entity, Id, Dto> {
    List<Entity> getAll();

    Optional<Entity> findById(Id id);

    Entity create(Dto academicGroup);

    Entity update(Dto updatedGroup);

    void deleteById(Id id);
}
