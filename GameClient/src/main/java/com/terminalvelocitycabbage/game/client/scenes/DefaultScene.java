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
import com.terminalvelocitycabbage.game.client.registry.GameModels;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

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

        var entity = manager.createEntity();
        entity.addComponent(ModelComponent.class).setModel(GameModels.SMILE_SQUARE_MODEL);
        entity.addComponent(TransformationComponent.class).setPosition(-1.25f, 0, -2);

        var entity2 = manager.createEntity();
        entity2.addComponent(ModelComponent.class).setModel(GameModels.SAD_SQUARE_MODEL);
        entity2.addComponent(TransformationComponent.class).setPosition(1.25f, 0, -2);

        playerEntity = manager.createEntity();
        playerEntity.addComponent(TransformationComponent.class);
        playerEntity.addComponent(PositionComponent.class);
        playerEntity.addComponent(PitchYawRotationComponent.class);
        playerEntity.addComponent(PlayerCameraComponent.class);
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
