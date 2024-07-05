package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameRoutines {

    public static Routine DEFAULT_ROUTINE;

    public static void init(GameClient client) {
        DEFAULT_ROUTINE = Routine.builder()
                //.addNode(client.identifierOf("updateSchedules"), UpdateScheduleSystem.class)
                .build();
    }

}
