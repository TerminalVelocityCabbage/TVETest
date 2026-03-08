package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.SceneRegistrationEvent;

public class GameScenes {

    public static Identifier DEFAULT_SCENE;

    public static void init(SceneRegistrationEvent event) {
        DEFAULT_SCENE = event.registerScene(GameClient.ID, "default", Scene.builder()
                .renderGraph(GameRenderers.DEFAULT_RENDER_GRAPH)
                .routines(GameRoutines.DEFAULT_ROUTINE)
                .inputControllers(GameInput.UI_CLICK, GameInput.RELOAD_SHADERS, GameInput.PAUSE_SPINNING, GameInput.MOVE_AROUND, GameInput.LOOK_AROUND, GameInput.SCROLL)
                .textureAtlases(GameTextures.DEFAULT_SCENE_ATLAS)
                .entities(manager -> {
                    manager.createEntityFromTemplate(GameEntities.SMILE_SQUARE_ENTITY);
                    manager.createEntityFromTemplate(GameEntities.SAD_SQUARE_ENTITY);
                    manager.createEntityFromTemplate(GameEntities.PLAYER_ENTITY);
                })
                .build());
    }

}
