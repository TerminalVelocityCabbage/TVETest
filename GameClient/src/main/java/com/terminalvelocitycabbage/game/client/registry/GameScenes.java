package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.scenes.DefaultScene;

import java.util.Collections;

public class GameScenes {

    public static Identifier DEFAULT_SCENE;

    public static void init(GameClient client) {
        DEFAULT_SCENE = client.identifierOf("default");
        client.getSceneRegistry().register(DEFAULT_SCENE, new DefaultScene(GameRenderers.DEFAULT_RENDER_GRAPH, Collections.singletonList(GameRoutines.DEFAULT_ROUTINE)));
    }

}
