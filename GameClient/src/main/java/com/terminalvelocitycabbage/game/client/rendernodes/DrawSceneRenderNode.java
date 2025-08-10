package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.materials.Texture;
import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.engine.util.HeterogeneousMap;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameRenderers;
import com.terminalvelocitycabbage.game.client.registry.GameStates;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class DrawSceneRenderNode extends RenderNode {

    public DrawSceneRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    //TODO add components for each of the registered vertex formats so that we can render
    @Override
    public void execute(Scene scene, WindowProperties properties, HeterogeneousMap renderConfig, long deltaTime) {

        //Set the shader up for rendering
        var client = GameClient.getInstance();
        var player = getPlayer();
        var camera = player.getComponent(PlayerCameraComponent.class);
        var shaderProgram = getShaderProgram();
        if (properties.isResized()) camera.updateProjectionMatrix(properties.getWidth(), properties.getHeight());
        shaderProgram.bind();
        shaderProgram.getUniform("textureSampler").setUniform(0);
        shaderProgram.getUniform("projectionMatrix").setUniform(camera.getProjectionMatrix());
        shaderProgram.getUniform("viewMatrix").setUniform(camera.getViewMatrix(player));

        if (client.getStateHandler().getState(GameStates.WIREFRAME_MODE).isEnabled()) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        //Sort entities for efficient rendering (by texture then by mesh)
        List<Entity> entities = new ArrayList<>(client.getManager().getEntitiesWith(ModelComponent.class, TransformationComponent.class));
        entities.sort(Comparator
                        .comparingInt((Entity entity) -> client.getTextureCache().getTexture(client.getModelRegistry().get(entity.getComponent(ModelComponent.class).getModel()).getTextureIdentifier()).getTextureID())
                        .thenComparing(entity -> client.getModelRegistry().get(entity.getComponent(ModelComponent.class).getModel()).getMeshIdentifier().hashCode())
        );

        //Render entities
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

        //Reset
        shaderProgram.unbind();
        if (renderConfig.get(GameRenderers.PRINT_ON_EXECUTE)) {
            Log.info("Rendered Frame");
        }
    }

    private Entity getPlayer() {
        return GameClient.getInstance().getManager().getFirstEntityWith(PlayerCameraComponent.class, PositionComponent.class, PitchYawRotationComponent.class);
    }
}
