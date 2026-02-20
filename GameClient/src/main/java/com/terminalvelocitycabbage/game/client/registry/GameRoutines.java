package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.ecs.RotateEntitiesSystem;
import com.terminalvelocitycabbage.templates.events.RoutineRegistrationEvent;

public class GameRoutines {

    //TODO replace with identifiers and retrieve from registry in scene init
    public static Routine DEFAULT_ROUTINE;

    public static void init(RoutineRegistrationEvent event) {
        DEFAULT_ROUTINE = event.registerRoutine(
                Routine.builder(GameClient.ID, "default")
                        .addStep(event.registerStep(GameClient.ID, "updateRotations"), RotateEntitiesSystem.class)
                        .build()
        );
    }

}
