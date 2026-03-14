package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.TargetProperties;
import com.terminalvelocitycabbage.engine.client.renderer.materials.Texture;
import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.engine.util.HeterogeneousMap;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GBufferRenderNode extends RenderNode {

    public GBufferRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    @Override
    public void render(Scene scene, TargetProperties properties, HeterogeneousMap renderConfig, long deltaTime) {

        var client = GameClient.getInstance();
        var player = getPlayer();
        var camera = player.getComponent(PlayerCameraComponent.class);
        var shaderProgram = getShaderProgram();
        if (properties.isResized()) camera.updateProjectionMatrix(properties.getWidth(), properties.getHeight());
        shaderProgram.bind();
        shaderProgram.getUniform("textureSampler").setUniform(0);
        shaderProgram.getUniform("projectionMatrix").setUniform(camera.getProjectionMatrix());
        shaderProgram.getUniform("viewMatrix").setUniform(camera.getViewMatrix(player));

        List<Entity> entities = new ArrayList<>(client.getManager().getEntitiesWith(ModelComponent.class, TransformationComponent.class));
        entities.sort(Comparator
                .comparingInt((Entity entity) -> client.getTextureCache().getTexture(client.getModelRegistry().get(entity.getComponent(ModelComponent.class).getModel()).getTextureIdentifier()).getTextureID())
                .thenComparing(entity -> client.getModelRegistry().get(entity.getComponent(ModelComponent.class).getModel()).getMeshIdentifier().hashCode())
        );

        Texture lastTexture = null;
        Mesh lastMesh = null;
        for (Entity entity : entities) {
            var modelIdentifier = entity.getComponent(ModelComponent.class).getModel();
            var model = client.getModelRegistry().get(modelIdentifier);
            var mesh = scene.getMeshCache().getMesh(modelIdentifier);
            var texture = client.getTextureCache().getTexture(model.getTextureIdentifier());
            var transformationComponent = entity.getComponent(TransformationComponent.class);

            if (lastTexture != texture) lastTexture = texture;
            if (lastMesh != mesh) lastMesh = mesh;

            lastTexture.bind();
            shaderProgram.getUniform("modelMatrix").setUniform(transformationComponent.getTransformationMatrix());
            if (mesh.getFormat().equals(shaderProgram.getConfig().getVertexFormat())) mesh.render();
        }

        shaderProgram.unbind();
    }

    private Entity getPlayer() {
        return GameClient.getInstance().getManager().getFirstEntityWith(PlayerCameraComponent.class, PositionComponent.class, PitchYawRotationComponent.class);
    }
}
