package com.terminalvelocitycabbage.game.client.scenes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.model.bedrock.BedrockModelData;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameModels;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;
import com.terminalvelocitycabbage.templates.ecs.components.MaterialComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

import java.util.List;

public class DefaultScene extends Scene {

    private Entity playerEntity;
    private Entity pigEntity;

    public DefaultScene(Identifier renderGraph, List<Routine> routines) {
        super(renderGraph, routines);
    }

    @Override
    public void init() {
        GameClient client = (GameClient) ClientBase.getInstance();
        Manager manager = client.getManager();

        client.getFileSystem().getResourceIdentifiersOfType(ResourceType.TEXTURE).forEach(identifier -> getTextureCache().createTexture(identifier));

        playerEntity = manager.createEntity();
        playerEntity.addComponent(TransformationComponent.class);

        pigEntity = manager.createEntity();
        pigEntity.addComponent(TransformationComponent.class).setPosition(0, 0, -20);
        pigEntity.addComponent(ModelComponent.class).setModel(BedrockModelData.Loader.loadModel(client.getFileSystem().getResource(ResourceType.MODEL, GameModels.TEST_PIG)).toModel());
        pigEntity.addComponent(MaterialComponent.class).setTexture(GameTextures.TEST_PIG);
    }

    @Override
    public void cleanup() {

    }
}
