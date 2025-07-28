package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.RenderGraph;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.HeterogeneousMap;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawSceneRenderNode;
import com.terminalvelocitycabbage.templates.events.RendererRegistrationEvent;

public class GameRenderers {

    //Render Node Identifiers
    public static Identifier DRAW_SCENE_RENDER_NODE = new Identifier(GameClient.ID, "drawScene");
    //Render Graph Identifiers
    public static Identifier DEFAULT_RENDER_GRAPH;
    //Graph configs
    public static HeterogeneousMap.Key<Boolean> PRINT_ON_EXECUTE = new HeterogeneousMap.Key<>("printOnExecute", Boolean.class);

    public static void init(RendererRegistrationEvent event) {

        var rotateSystemRoute = RenderGraph.RenderPath.builder().addRoutineNode(new Identifier(GameClient.ID, "updateRotations"), GameRoutines.DEFAULT_ROUTINE);
        var emptyRoute = RenderGraph.RenderPath.builder();

        DEFAULT_RENDER_GRAPH = event.register(new Identifier(GameClient.ID, "drawScene"),
                new RenderGraph(RenderGraph.RenderPath.builder()
                        .route(new Identifier(GameClient.ID, "booleanRoute"), (capabilities) -> true,
                                rotateSystemRoute,
                                emptyRoute)
                        .addRenderNode(DRAW_SCENE_RENDER_NODE, DrawSceneRenderNode.class, GameShaders.MESH_SHADER_PROGRAM_CONFIG)
                        .configure(PRINT_ON_EXECUTE, false)
                )
        ).getIdentifier();
    }

}

