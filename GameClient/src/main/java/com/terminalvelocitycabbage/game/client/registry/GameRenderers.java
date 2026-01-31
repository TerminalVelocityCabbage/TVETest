package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.RenderGraph;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.HeterogeneousMap;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawSceneRenderNode;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawTestTWUIRenderNode;
import com.terminalvelocitycabbage.templates.events.RendererRegistrationEvent;

public class GameRenderers {

    //Render Node Identifiers
    public static Identifier DRAW_SCENE_RENDER_NODE;
    public static Identifier DRAW_UI_RENDER_NODE;

    //Routine Node Identifiers
    public static Identifier UPDATE_ROTATIONS_ROUTINE_NODE;

    //Route Identifiers
    public static Identifier BOOLEAN_ROUTE;

    //Render Graph Identifiers
    public static Identifier DEFAULT_RENDER_GRAPH;
    //Graph configs
    public static HeterogeneousMap.Key<Boolean> PRINT_ON_EXECUTE = new HeterogeneousMap.Key<>("printOnExecute", Boolean.class);

    public static void init(RendererRegistrationEvent event) {

        //Render Nodes
        DRAW_SCENE_RENDER_NODE = event.registerNode(GameClient.ID, "drawScene");
        DRAW_UI_RENDER_NODE = event.registerNode(GameClient.ID, "drawUI");
        //Routines
        UPDATE_ROTATIONS_ROUTINE_NODE = event.registerNode(GameClient.ID, "updateRotations");
        //Routes
        BOOLEAN_ROUTE = event.registerRoute(GameClient.ID, "booleanRoute");

        var rotateSystemRoute = RenderGraph.RenderPath.builder().addRoutineNode(UPDATE_ROTATIONS_ROUTINE_NODE, GameRoutines.DEFAULT_ROUTINE);

        DEFAULT_RENDER_GRAPH = event.registerGraph(GameClient.ID, "draw_scene",
                new RenderGraph(RenderGraph.RenderPath.builder()
                        .route(
                                BOOLEAN_ROUTE,
                                (__, sh) -> (boolean) sh.getState(GameStates.ROTATE_ENTITIES).getValue(),
                                rotateSystemRoute)
                        .addRenderNode(DRAW_SCENE_RENDER_NODE, DrawSceneRenderNode.class, GameShaders.MESH_SHADER_PROGRAM_CONFIG)
                        .addRenderNode(DRAW_UI_RENDER_NODE, DrawTestTWUIRenderNode.class, ShaderProgramConfig.EMPTY)
                        .configure(PRINT_ON_EXECUTE, false)
                )
        );
    }

}

