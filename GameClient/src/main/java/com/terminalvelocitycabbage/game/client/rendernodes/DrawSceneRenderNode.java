package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.materials.TextureCache;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgram;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.ecs.ComponentFilter;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.ecs.MaterialComponent;
import com.terminalvelocitycabbage.game.client.ecs.MeshComponent;

public class DrawSceneRenderNode extends RenderNode {

    private static final ComponentFilter RENDERABLE_ENTITIES = ComponentFilter.builder().anyOf(MeshComponent.class).build();

    //TODO add components for each of the registered vertex formats so that we can render
    @Override
    public void executeRenderStage(WindowProperties properties, long deltaTime, ShaderProgram shaderProgram) {
        shaderProgram.bind();
        shaderProgram.getUniform("textureSampler").setUniform(0);
        GameClient.getInstance().getManager().getMatchingEntities(RENDERABLE_ENTITIES).forEach(entity -> {
            var mesh = entity.getComponent(MeshComponent.class).getMesh();
            var textureId = entity.getComponent(MaterialComponent.class).getTexture();
            GameClient.getInstance().getTextureCache().getTexture(textureId).bind();
            if (mesh.getFormat().equals(shaderProgram.getConfig().getVertexFormat())) mesh.render();
        });
        shaderProgram.unbind();
    }
}
