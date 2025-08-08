package com.terminalvelocitycabbage.game.client.scenes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.materials.TextureCache;
import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.client.renderer.model.Vertex;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.MaterialComponent;
import com.terminalvelocitycabbage.templates.ecs.components.MeshComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

import java.util.List;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;
import static com.terminalvelocitycabbage.game.client.registry.GameTextures.SMILE;

public class DefaultScene extends Scene {

    private Entity playerEntity;

    //TODO replace these with a model/mesh registered to a model/mesh cache so we can sort the meshes needing to be rendered by type and reduce the number of mesh binds
    private static final Vertex[] TEST_VERTICES = new Vertex[] {
            new Vertex(MESH_FORMAT)
                    .setXYZPosition(-1f, 1f, 0f)
                    .setRGBColor(0.5f, 0.0f, 0.0f)
                    .setUV(0, 0),
            new Vertex(MESH_FORMAT)
                    .setXYZPosition(-1f, -1f, 0f)
                    .setRGBColor(0.0f, 0.5f, 0.0f)
                    .setUV(0, 1),
            new Vertex(MESH_FORMAT)
                    .setXYZPosition(1f, -1f, 0f)
                    .setRGBColor(0.0f, 0.0f, 0.5f)
                    .setUV(1, 1),
            new Vertex(MESH_FORMAT)
                    .setXYZPosition(1f, 1f, 0f)
                    .setRGBColor(0.0f, 0.5f, 0.5f)
                    .setUV(1, 0)
    };
    private static final int[] TEST_INDEXES = new int[] {0, 1, 3, 3, 1, 2};

    public DefaultScene(Identifier renderGraph, Routine... routines) {
        super(renderGraph, List.of(routines));
    }

    @Override
    public void init() {
        GameClient client = (GameClient) ClientBase.getInstance();
        Manager manager = client.getManager();

        //TODO replace with registry for meshes. mesh should include an identifier to a texture
        //TODO replace component Mesh field with an identifier for that mesh
        var entity = manager.createEntity();
        entity.addComponent(MeshComponent.class).setMesh(new Mesh(MESH_FORMAT, TEST_VERTICES, TEST_INDEXES));
        entity.addComponent(MaterialComponent.class).setTexture(SMILE);
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
}
