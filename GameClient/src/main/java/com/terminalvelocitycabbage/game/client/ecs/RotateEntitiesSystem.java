package com.terminalvelocitycabbage.game.client.ecs;

import com.terminalvelocitycabbage.engine.ecs.ComponentFilter;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.System;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

import java.util.List;

public class RotateEntitiesSystem extends System {

    public static final ComponentFilter TRANSFORMABLE_ENTITIES = ComponentFilter.builder().anyOf(TransformationComponent.class).build();

    @Override
    public void update(List<Entity> entities, float deltaTime) {
        entities.forEach(entity -> entity.getComponent(TransformationComponent.class).rotate((float) Math.sin(deltaTime * 5f) / 2.5f, 0.5f, 0));
    }
}
