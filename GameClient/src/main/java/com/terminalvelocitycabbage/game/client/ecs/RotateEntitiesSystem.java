package com.terminalvelocitycabbage.game.client.ecs;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.ecs.System;
import com.terminalvelocitycabbage.game.client.registry.GameStates;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

public class RotateEntitiesSystem extends System {

    @Override
    public void update(Manager manager, float deltaTime) {
        if (ClientBase.getInstance().getStateHandler().<Boolean>getState(GameStates.ROTATE_ENTITIES).getValue()) {
            manager.getEntitiesWith(TransformationComponent.class).forEach(entity -> {
                entity.getComponent(TransformationComponent.class).rotate(0, 1f, 0);
            });
        }
    }
}
