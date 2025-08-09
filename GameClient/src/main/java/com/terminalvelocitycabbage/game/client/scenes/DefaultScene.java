package com.terminalvelocitycabbage.game.client.scenes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.model.MeshCache;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameEntities;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;

import java.util.List;

public class DefaultScene extends Scene {

    private Entity playerEntity;

    public DefaultScene(Identifier renderGraph, Routine... routines) {
        super(renderGraph, List.of(routines));
    }

    @Override
    public void init() {
        GameClient client = (GameClient) ClientBase.getInstance();
        Manager manager = client.getManager();

        client.getTextureCache().generateAtlas(GameTextures.DEFAULT_SCENE_ATLAS);
        setMeshCache(new MeshCache(client.getModelRegistry(), client.getMeshRegistry(), client.getTextureCache()));

        manager.createEntityFromTemplate(GameEntities.SMILE_SQUARE_ENTITY);
        manager.createEntityFromTemplate(GameEntities.SAD_SQUARE_ENTITY);
        playerEntity = manager.createEntityFromTemplate(GameEntities.PLAYER_ENTITY);
    }

    @Override
    public void cleanup() {
        GameClient.getInstance().getTextureCache().cleanupAtlas(GameTextures.DEFAULT_SCENE_ATLAS);
        getMeshCache().cleanup();
    }
}
