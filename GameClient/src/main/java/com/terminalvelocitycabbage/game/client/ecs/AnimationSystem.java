package com.terminalvelocitycabbage.game.client.ecs;

import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.ecs.System;
import com.terminalvelocitycabbage.templates.ecs.components.AnimationComponent;

public class AnimationSystem extends System {

    @Override
    public void update(Manager manager, float deltaTime) {
        manager.getEntitiesWith(AnimationComponent.class).forEach(entity -> {
            entity.getComponent(AnimationComponent.class).update(deltaTime / 1000.0f);
        });
    }
}
