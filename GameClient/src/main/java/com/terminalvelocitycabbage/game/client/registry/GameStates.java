package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.GameStateRegistrationEvent;

public class GameStates {

    public static Identifier ROTATE_ENTITIES;
    public static Identifier DEBUG_RENDERER;

    public static void registerStates(GameStateRegistrationEvent event) {
        ROTATE_ENTITIES = event.registerState(GameClient.ID, "rotate_entities", true);
        DEBUG_RENDERER = event.registerState(GameClient.ID, "debug_renderer", false);
    }

}
