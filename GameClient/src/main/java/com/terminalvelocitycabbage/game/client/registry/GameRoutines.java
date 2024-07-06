package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.ecs.RotateEntitiesSystem;

public class GameRoutines {

    public static Routine DEFAULT_ROUTINE;

    public static void init(GameClient client) {
        DEFAULT_ROUTINE = Routine.builder()
                .addNode(client.identifierOf("updateRotations"), RotateEntitiesSystem.class, RotateEntitiesSystem.TRANSFORMABLE_ENTITIES)
                .build();
    }

}
