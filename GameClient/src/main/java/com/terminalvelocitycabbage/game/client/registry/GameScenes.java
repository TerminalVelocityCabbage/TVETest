package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.SceneRegistrationEvent;

public class GameScenes {

    public static Identifier DEFAULT_SCENE;

    public static void init(SceneRegistrationEvent event) {
        DEFAULT_SCENE = event.registerSceneFromFile(GameClient.ID, "default");
    }

    public static void registerResources(ResourceRegistrationEvent event) {
        event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.SCENE, "default.scene.toml");
    }

}
