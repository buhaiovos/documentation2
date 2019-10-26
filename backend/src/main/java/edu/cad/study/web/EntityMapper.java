package edu.cad.study.web;

import edu.cad.utils.gson.Option;

public interface EntityMapper<Entity, Dto> {
    Dto toResponse(Entity e);

    Option toOption(Entity e);
}
