package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.model.Model;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.HeterogeneousMap;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameRenderers;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        //Sort entities for efficient rendering (by texture then by model)
        List<Entity> entities = new ArrayList<>(client.getManager().getEntitiesWith(ModelComponent.class, TransformationComponent.class));
        entities.sort(Comparator
                        .comparingInt((Entity entity) -> client.getTextureCache().getTexture(client.getModelRegistry().get(entity.getComponent(ModelComponent.class).getModel()).textureIdentifier()).getTextureID())
                        .thenComparing(entity -> entity.getComponent(ModelComponent.class).getModel().hashCode())
        );

        //Render entities
        Identifier lastTextureID = null;
        Identifier lastModelID = null;
        Model model;
        for (Entity entity : entities) {

            //Update the transformation to that of this entity
            shaderProgram.getUniform("modelMatrix").setUniform(entity.getComponent(TransformationComponent.class).getTransformationMatrix());

            //Early draw if this is the same model as the last entity (save on uploads)
            var modelIdentifier = entity.getComponent(ModelComponent.class).getModel();
            if (modelIdentifier.equals(lastModelID)) {
                client.getModelRegistry().get(modelIdentifier).draw();
                continue;
            } else {
                lastModelID = modelIdentifier;
                model = client.getModelRegistry().get(modelIdentifier);
            }

            if (model.compiledMesh().getFormat().equals(shaderProgram.getConfig().getVertexFormat())) {
                //Optimization: only bind texture and mesh if they've changed since the last entity
                var textureIdentifier = model.textureIdentifier();
                if (!textureIdentifier.equals(lastTextureID)) {
                    model.bindTexture(client.getTextureCache());
                    lastTextureID = textureIdentifier;
                }

                model.bind();
                model.draw();
            } else {
                Log.error("Model " + modelIdentifier + " has a different vertex format than the shader program!");
            }
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
