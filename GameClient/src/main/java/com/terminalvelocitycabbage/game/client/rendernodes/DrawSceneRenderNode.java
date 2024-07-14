package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.Projection;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.ecs.ComponentFilter;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.ecs.components.MaterialComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

import static org.lwjgl.opengl.GL11.*;

public class DrawSceneRenderNode extends RenderNode {

    private static final ComponentFilter RENDERABLE_ENTITIES = ComponentFilter.builder().allOf(ModelComponent.class, TransformationComponent.class, MaterialComponent.class).build();
    private static final Projection PERSPECTIVE = new Projection(Projection.Type.PERSPECTIVE, 60, 0.1f, 1000f);

    public DrawSceneRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    //TODO add components for each of the registered vertex formats so that we can render
    //TODO add a way to sort entities in the renderer by model type so we can avoid extra binding of meshes
    @Override
    public void execute(Scene scene, WindowProperties properties, long deltaTime) {
        var client = GameClient.getInstance();
        var shaderProgram = getShaderProgram();
        if (properties.isResized()) PERSPECTIVE.updateProjectionMatrix(properties.getWidth(), properties.getHeight());
        shaderProgram.bind();
        shaderProgram.getUniform("textureSampler").setUniform(0);
        shaderProgram.getUniform("projectionMatrix").setUniform(PERSPECTIVE.getProjectionMatrix());
        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        //glDisable(GL_DEPTH_TEST);
        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        client.getManager().getMatchingEntities(RENDERABLE_ENTITIES).forEach(entity -> {
            var model = entity.getComponent(ModelComponent.class).getModel();
            var textureId = entity.getComponent(MaterialComponent.class).getTexture();
            var transformationComponent = entity.getComponent(TransformationComponent.class);
            if (transformationComponent.isDirty()) transformationComponent.updateTransformationMatrix();
            shaderProgram.getUniform("modelMatrix").setUniform(transformationComponent.getTransformationMatrix());
            scene.getTextureCache().getTexture(textureId).bind();
            if (model.getFormat().equals(shaderProgram.getConfig().getVertexFormat())) model.render();
        });
        shaderProgram.unbind();
    }
}
