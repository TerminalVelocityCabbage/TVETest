package com.terminalvelocitycabbage.game.common.ecs;

import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.System;

import java.util.List;

public class UpdatePositionsSystem extends System {

    @Override
    public void update(List<Entity> entities, float deltaTime) {
        for (Entity entity : entities) {
            var positions = entity.getComponent(PositionComponent.class).getPosition().add(0, -1, 0); //Gravity or some shit idk
            Log.info(positions);
        }
    }
}
