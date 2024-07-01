package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.elements.VertexAttribute;
import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.client.renderer.model.Vertex;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.ecs.MaterialComponent;
import com.terminalvelocitycabbage.game.client.ecs.MeshComponent;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

public class GameEntities {

    public static void init(GameClient client) {
        client.getManager().registerComponent(MeshComponent.class);
        client.getManager().registerComponent(MaterialComponent.class);
        Vertex[] vertices = new Vertex[] {
                Vertex.builder()
                        .addAttribute(VertexAttribute.XYZ_POSITION, new float[]{-0.5f, 0.5f, 0.0f})
                        .addAttribute(VertexAttribute.RGB_COLOR, new float[]{0.5f, 0.0f, 0.0f})
                        .addAttribute(VertexAttribute.UV, new float[]{0, 0})
                        .build(),
                Vertex.builder()
                        .addAttribute(VertexAttribute.XYZ_POSITION, new float[]{-0.5f, -0.5f, 0.0f})
                        .addAttribute(VertexAttribute.RGB_COLOR, new float[]{0.0f, 0.5f, 0.0f})
                        .addAttribute(VertexAttribute.UV, new float[]{0, 1})
                        .build(),
                Vertex.builder()
                        .addAttribute(VertexAttribute.XYZ_POSITION, new float[]{0.5f, -0.5f, 0.0f})
                        .addAttribute(VertexAttribute.RGB_COLOR, new float[]{0.0f, 0.0f, 0.5f})
                        .addAttribute(VertexAttribute.UV, new float[]{1, 1})
                        .build(),
                Vertex.builder()
                        .addAttribute(VertexAttribute.XYZ_POSITION, new float[]{0.5f, 0.5f, 0.0f})
                        .addAttribute(VertexAttribute.RGB_COLOR, new float[]{0.0f, 0.5f, 0.5f})
                        .addAttribute(VertexAttribute.UV, new float[]{1, 0})
                        .build(),
        };
        int[] indices = new int[] {0, 1, 3, 3, 1, 2};

        //TODO replace with registries for textures
        //TODO replace with registry for meshes. mesh should include an identifier to a texture
        //TODO replace component Mesh field with an identifier for that mesh
        //TODO abstract our texture registry to a Texture cache
        //TODO texture cache should be configured to create a set of atlases or a texture array. For MC block textures a texture array is better, but for entities they're too different
        //TODO Texture cache should create Texture objects
        var entity = client.getManager().createEntity();
        entity.addComponent(MeshComponent.class).setMesh(new Mesh(MESH_FORMAT, vertices, indices));
        entity.addComponent(MaterialComponent.class).setTexture(GameTextures.SMILE);
    }

}
