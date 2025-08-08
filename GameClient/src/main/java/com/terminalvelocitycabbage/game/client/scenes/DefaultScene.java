package com.terminalvelocitycabbage.game.client.scenes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.materials.TextureCache;
import com.terminalvelocitycabbage.engine.client.renderer.model.MeshCache;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameEntities;

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

        manager.createEntityFromTemplate(GameEntities.SMILE_SQUARE_ENTITY);
        manager.createEntityFromTemplate(GameEntities.SAD_SQUARE_ENTITY);
        playerEntity = manager.createEntityFromTemplate(GameEntities.PLAYER_ENTITY);
    }

    @Override
    public TextureCache createTextureCache() {

        Identifier entityAtlas = ClientBase.getInstance().identifierOf("entityAtlas");

        var builder = TextureCache.builder();
        builder.addAtlas(entityAtlas);
        //Add all textures to this atlas
        //TODO add a way to flag resources on registration or add some utility to just addTexture from an identifier instead of needing to specify the id and resource
        ClientBase.getInstance().getFileSystem().getResourcesOfType(ResourceCategory.TEXTURE).forEach((key, value) -> {
            builder.addTexture(key, value, entityAtlas);
        });

        return builder.build();
    }

    @Override
    public MeshCache createMeshCache() {
        var client = ClientBase.getInstance();
        return new MeshCache(client.getModelRegistry(), client.getMeshRegistry(), getTextureCache());
    }

    @Override
    public void cleanup() {
        getTextureCache().cleanup();
        getMeshCache().cleanup();
    }
}
