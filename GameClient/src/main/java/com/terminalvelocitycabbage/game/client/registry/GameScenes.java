package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.scenes.DefaultScene;
import com.terminalvelocitycabbage.templates.events.SceneRegistrationEvent;

public class GameScenes {

    public static Identifier DEFAULT_SCENE;

    public static void init(SceneRegistrationEvent event) {
        DEFAULT_SCENE = event.register(
                new Identifier(GameClient.ID, "default"),
                new DefaultScene(GameRenderers.DEFAULT_RENDER_GRAPH, GameRoutines.DEFAULT_ROUTINE)
        ).getIdentifier();
    }

}
