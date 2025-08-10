package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.GameStateRegistrationEvent;

public class GameStates {

    public static Identifier ROTATE_ENTITIES;
    public static Identifier WIREFRAME_MODE;

    public static void registerStates(GameStateRegistrationEvent event) {
        ROTATE_ENTITIES = event.registerState(ClientBase.getInstance().identifierOf("rotate_entities"), true);
        WIREFRAME_MODE = event.registerState(ClientBase.getInstance().identifierOf("wireframe_mode"), false);
    }

}
