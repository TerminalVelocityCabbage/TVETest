package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.Projection;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.ecs.ComponentFilter;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.ecs.MaterialComponent;
import com.terminalvelocitycabbage.game.client.ecs.MeshComponent;
import com.terminalvelocitycabbage.game.client.ecs.TransformationComponent;

public class DrawSceneRenderNode extends RenderNode {

    private static final ComponentFilter RENDERABLE_ENTITIES = ComponentFilter.builder().allOf(MeshComponent.class, TransformationComponent.class).build();
    private static final Projection PERSPECTIVE = new Projection(Projection.Type.PERSPECTIVE, 60, 0.1f, 1000f);

    //TODO add components for each of the registered vertex formats so that we can render
    //TODO add a way to sort entities in the renderer by model type so we can avoid extra binding of meshes
    @Override
    public void executeRenderStage(Scene scene, WindowProperties properties, long deltaTime) {
        var client = GameClient.getInstance();
        var renderGraph = client.getRenderGraphRegistry().get(scene.getRenderGraph());
        var shaderProgram = renderGraph.getShaderProgram();
        if (properties.isResized()) PERSPECTIVE.updateProjectionMatrix(properties.getWidth(), properties.getHeight());
        shaderProgram.bind();
        shaderProgram.getUniform("textureSampler").setUniform(0);
        shaderProgram.getUniform("projectionMatrix").setUniform(PERSPECTIVE.getProjectionMatrix());
        client.getManager().getMatchingEntities(RENDERABLE_ENTITIES).forEach(entity -> {
            var mesh = entity.getComponent(MeshComponent.class).getMesh();
            var textureId = entity.getComponent(MaterialComponent.class).getTexture();
            var transformationComponent = entity.getComponent(TransformationComponent.class);
            if (transformationComponent.isDirty()) transformationComponent.updateTransformationMatrix();
            shaderProgram.getUniform("modelMatrix").setUniform(transformationComponent.getTransformationMatrix());
            scene.getTextureCache().getTexture(textureId).bind();
            if (mesh.getFormat().equals(shaderProgram.getConfig().getVertexFormat())) mesh.render();
        });
        shaderProgram.unbind();
    }
}
