package edu.cad.study;

import edu.cad.utils.Option;

public interface EntityMapper<Entity, Dto> {
    Dto toResponse(Entity e);

    Option toOption(Entity e);
}
