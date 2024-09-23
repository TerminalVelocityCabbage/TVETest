package com.terminalvelocitycabbage.game.client.scenes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.animation.bedrock.BedrockAnimationData;
import com.terminalvelocitycabbage.engine.client.renderer.model.bedrock.BedrockModelData;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.filesystem.GameFileSystem;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameAnimations;
import com.terminalvelocitycabbage.game.client.registry.GameModels;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;
import com.terminalvelocitycabbage.templates.ecs.components.MaterialComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelAnimationControllerComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;
import org.joml.Vector3f;

import java.util.List;

public class DefaultScene extends Scene {

    private Entity playerEntity;
    private Entity pigEntity;
    private Entity quaxlyEntity;

    public DefaultScene(Identifier renderGraph, Routine... routines) {
        super(renderGraph, List.of(routines));
    }

    @Override
    public void init() {
        GameClient client = (GameClient) ClientBase.getInstance();
        Manager manager = client.getManager();
        GameFileSystem fileSystem = client.getFileSystem();

        client.getFileSystem().getResourcesOfType(ResourceCategory.TEXTURE).forEach((key, value) -> getTextureCache().createTexture(key, value));

        playerEntity = manager.createEntity();
        playerEntity.addComponent(TransformationComponent.class);

        pigEntity = createDefaultModelEntity(manager, fileSystem, GameModels.TEST_PIG, GameTextures.TEST_PIG, GameAnimations.TEST_PIG, new Vector3f(0, -5, -20));
        pigEntity.getComponent(ModelAnimationControllerComponent.class).getAnimationController().getAnimation("Walk").loop();
        //quaxlyEntity = createDefaultModelEntity(manager, fileSystem, GameModels.QUAXLY, GameTextures.QUAXLY, null, new Vector3f(10, -5, -20));
    }

    //TODO replace all classes in registry package with a resources class to just register expected resources
    //TODO replace this with overload-able methods in the client for registering models and animations to be retrieved by identifier
    private Entity createDefaultModelEntity(Manager manager, GameFileSystem fileSystem, Identifier model, Identifier texture, Identifier animations, Vector3f initialPosition) {
        var tempEntity = manager.createEntity();
        tempEntity.addComponent(TransformationComponent.class).setPosition(initialPosition);
        var loadedModel = BedrockModelData.Loader.loadModel(fileSystem.getResource(ResourceCategory.MODEL, model)).toModel();
        var animationController = animations == null ? null : BedrockAnimationData.Loader.loadAnimations(fileSystem.getResource(ResourceCategory.ANIMATION, animations)).toAnimationController(loadedModel);
        tempEntity.addComponent(ModelComponent.class).setModel(loadedModel);
        tempEntity.addComponent(ModelAnimationControllerComponent.class).setAnimationController(animationController);
        tempEntity.addComponent(MaterialComponent.class).setTexture(texture);
        return tempEntity;
    }

    @Override
    public void cleanup() {

    }
}
