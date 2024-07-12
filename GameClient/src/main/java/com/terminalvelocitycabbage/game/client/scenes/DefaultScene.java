package com.terminalvelocitycabbage.game.client.scenes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

import java.util.List;

public class DefaultScene extends Scene {

    private Entity playerEntity;

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
    }

    @Override
    public void cleanup() {

    }
}
