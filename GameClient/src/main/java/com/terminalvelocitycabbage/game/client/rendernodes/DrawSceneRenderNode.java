package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgram;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.ecs.ComponentFilter;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.ecs.MeshComponent;

public class DrawSceneRenderNode extends RenderNode {

    private static final ComponentFilter RENDERABLE_ENTITIES = ComponentFilter.builder().anyOf(MeshComponent.class).build();

    @Override
    public void executeRenderStage(WindowProperties properties, long deltaTime, ShaderProgram shaderProgram) {
        shaderProgram.bind();
        GameClient.getInstance().getManager().getMatchingEntities(RENDERABLE_ENTITIES).forEach(entity -> {
            entity.getComponent(MeshComponent.class).getMesh().render();
        });
        shaderProgram.unbind();
    }
}
