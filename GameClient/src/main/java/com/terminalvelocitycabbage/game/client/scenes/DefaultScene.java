package com.terminalvelocitycabbage.game.client.scenes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.materials.Texture;
import com.terminalvelocitycabbage.engine.client.renderer.materials.TextureCache;
import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.client.renderer.model.MeshCache;
import com.terminalvelocitycabbage.engine.client.renderer.model.Model;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.registry.Registry;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameModels;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;
import com.terminalvelocitycabbage.templates.events.MeshRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.ModelConfigRegistrationEvent;

import java.util.List;

public class DefaultScene extends Scene {

    private Entity playerEntity;
    private Registry<Mesh> meshRegistry;
    private MeshCache meshCache;
    private Registry<Model> modelRegistry;

    public DefaultScene(Identifier renderGraph, Routine... routines) {
        super(renderGraph, List.of(routines));

        this.meshRegistry = new Registry<>();
        this.modelRegistry = new Registry<>();
    }

    @Override
    public void init() {
        GameClient client = (GameClient) ClientBase.getInstance();
        Manager manager = client.getManager();

        client.getEventDispatcher().dispatchEvent(new MeshRegistrationEvent(meshRegistry));
        client.getEventDispatcher().dispatchEvent(new ModelConfigRegistrationEvent(modelRegistry));

        //Generate mesh cache
        meshCache = new MeshCache(modelRegistry, meshRegistry, getTextureCache());

        //TODO replace with registry for meshes. mesh should include an identifier to a texture
        //TODO replace component Mesh field with an identifier for that mesh
        var entity = manager.createEntity();
        entity.addComponent(ModelComponent.class).setModel(GameModels.SMILE_SQUARE_MODEL);
        entity.addComponent(TransformationComponent.class).setPosition(0, 0, -2);

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
    public void cleanup() {
        getTextureCache().cleanup();
    }

    @Override
    public Mesh getMesh(Identifier model) {
        return meshCache.getMesh(model);
    }

    @Override
    public Texture getTexture(Identifier model) {
        return getTextureCache().getTexture(modelRegistry.get(model).getTextureIdentifier());
    }
}
