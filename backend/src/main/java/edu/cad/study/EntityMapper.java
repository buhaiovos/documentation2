package edu.cad.study;

public interface EntityMapper<Entity, Dto> {
    Dto toResponse(Entity e);

    DropdownOption toOption(Entity e);
}
