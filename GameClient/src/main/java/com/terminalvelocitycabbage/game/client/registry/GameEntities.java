package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.client.renderer.model.Vertex;
import com.terminalvelocitycabbage.game.client.ecs.RotateEntitiesSystem;
import com.terminalvelocitycabbage.templates.ecs.components.MaterialComponent;
import com.terminalvelocitycabbage.templates.ecs.components.MeshComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;
import com.terminalvelocitycabbage.templates.events.EntityComponentRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.EntitySystemRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.EntityTemplateRegistrationEvent;

public class GameEntities {

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

    public static void registerComponents(EntityComponentRegistrationEvent event) {
        event.registerComponent(MeshComponent.class);
        event.registerComponent(MaterialComponent.class);
        event.registerComponent(TransformationComponent.class);
    }

    public static void createSystems(EntitySystemRegistrationEvent event) {
        event.createSystem(RotateEntitiesSystem.class);
    }

    public static void createEntityTemplates(EntityTemplateRegistrationEvent event) {
        //TODO replace with registries for textures
        //TODO replace with registry for meshes. mesh should include an identifier to a texture
        //TODO replace component Mesh field with an identifier for that mesh
        //TODO abstract our texture registry to a Texture cache
        //TODO texture cache should be configured to create a set of atlases or a texture array. For MC block textures a texture array is better, but for entities they're too different
        //TODO Texture cache should create Texture objects
        var entity = event.createEntity();
        entity.addComponent(MeshComponent.class).setMesh(new Mesh(MESH_FORMAT, TEST_VERTICES, TEST_INDEXES));
        entity.addComponent(MaterialComponent.class).setTexture(GameTextures.SMILE);
        entity.addComponent(TransformationComponent.class).setPosition(0, 0, -2);
    }
}
