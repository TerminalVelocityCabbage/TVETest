package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.Projection;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.HeterogeneousMap;
import com.terminalvelocitycabbage.engine.util.touples.Pair;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameRenderers;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

import java.util.*;

public class DrawSceneRenderNode extends RenderNode {

    private static final Projection PERSPECTIVE = new Projection(Projection.Type.PERSPECTIVE, 60, 0.1f, 1000f);

    public DrawSceneRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    //TODO add components for each of the registered vertex formats so that we can render
    //TODO add a way to sort entities in the renderer by model type so we can avoid extra binding of meshes
    @Override
    public void execute(Scene scene, WindowProperties properties, HeterogeneousMap renderConfig, long deltaTime) {
        var client = GameClient.getInstance();
        var player = getPlayer();
        var camera = player.getComponent(PlayerCameraComponent.class);
        var shaderProgram = getShaderProgram();
        if (properties.isResized()) camera.updateProjectionMatrix(properties.getWidth(), properties.getHeight());
        shaderProgram.bind();
        shaderProgram.getUniform("textureSampler").setUniform(0);
        shaderProgram.getUniform("projectionMatrix").setUniform(camera.getProjectionMatrix());
        shaderProgram.getUniform("viewMatrix").setUniform(camera.getViewMatrix(player));

        //Collect entities for rendering
        var entities = client.getManager().getEntitiesWith(ModelComponent.class, TransformationComponent.class);
        Map<Identifier, List<Pair<ModelComponent, TransformationComponent>>> renderableEntities = new HashMap<>();

        //Sort entities by texture to reduce texture binds
        entities.forEach(entity -> {
            var modelComponent = entity.getComponent(ModelComponent.class);
            var model = client.getModelRegistry().get(modelComponent.getModel());
            var textureIdentifier = model.getTextureIdentifier();
            if (!renderableEntities.containsKey(textureIdentifier)) renderableEntities.put(textureIdentifier, new ArrayList<>());
            renderableEntities.get(textureIdentifier).add(new Pair<>(modelComponent, entity.getComponent(TransformationComponent.class)));
        });

        //Render each set of entities in order of texture then in order of mesh identifier
        renderableEntities.forEach((identifier, entitiesWithThisTexture) -> {
            scene.getTextureCache().getTexture(identifier).bind();
            //Sort this section of entities by mesh identifier
            entitiesWithThisTexture.stream().sorted(Comparator.comparingInt(value -> client.getModelRegistry().get(value.getValue0().getModel()).getMeshIdentifier().hashCode())).forEach(pair -> {
                //Render
                var mesh = scene.getMeshCache().getMesh(pair.getValue0().getModel());
                var transformationComponent = pair.getValue1();
                shaderProgram.getUniform("modelMatrix").setUniform(transformationComponent.getTransformationMatrix());
                if (mesh.getFormat().equals(shaderProgram.getConfig().getVertexFormat())) mesh.render();
            });
        });
        shaderProgram.unbind();
        if (renderConfig.get(GameRenderers.PRINT_ON_EXECUTE)) {
            Log.info("Rendered Frame");
        }
    }

    private Entity getPlayer() {
        return GameClient.getInstance().getManager().getFirstEntityWith(PlayerCameraComponent.class, PositionComponent.class, PitchYawRotationComponent.class);
    }
}
