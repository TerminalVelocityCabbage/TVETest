package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.RenderGraph;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.HeterogeneousMap;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.rendernodes.DebugGBufferRenderNode;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawSceneRenderNode;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawTestTWUIRenderNode;
import com.terminalvelocitycabbage.game.client.rendernodes.GBufferRenderNode;
import com.terminalvelocitycabbage.templates.events.FramebufferRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.RendererRegistrationEvent;

public class GameRenderers {

    //Render Node Identifiers
    public static Identifier DRAW_SCENE_RENDER_NODE;
    public static Identifier DRAW_FBO_SCENE_RENDER_NODE;
    public static Identifier DRAW_GBUFFER_NODE;
    public static Identifier DRAW_DEBUG_GBUFFER_NODE;
    public static Identifier DRAW_UI_RENDER_NODE;
    public static Identifier SCENE_FBO_ID;
    public static Identifier GBUFFER_FBO_ID;

    //Routine Node Identifiers
    public static Identifier UPDATE_ROTATIONS_ROUTINE_NODE;

    //Route Identifiers
    public static Identifier BOOLEAN_ROUTE;
    public static Identifier DEBUG_ROUTE;
    public static Identifier GBUFFER_DEBUG_ROUTE;
    public static Identifier SCENE_FBO_DEBUG_ROUTE;

    //Render Graph Identifiers
    public static Identifier DEFAULT_RENDER_GRAPH;
    //Graph configs
    public static HeterogeneousMap.Key<Boolean> PRINT_ON_EXECUTE = new HeterogeneousMap.Key<>("printOnExecute", Boolean.class);

    public static void registerFramebuffers(FramebufferRegistrationEvent event) {
        SCENE_FBO_ID = event.registerFramebuffer(GameClient.ID, "scene_fbo", 200, 200, GameTextures.SCENE_FBO_TEXTURE);
        GBUFFER_FBO_ID = event.registerDepthFramebuffer(GameClient.ID, "gbuffer_fbo", 800, 600, GameTextures.DEPTH_TEXTURE,
                GameTextures.POSITION_TEXTURE, GameTextures.NORMALS_TEXTURE, GameTextures.ALBEDO_TEXTURE);
    }

    public static void init(RendererRegistrationEvent event) {

        //Render Nodes
        DRAW_SCENE_RENDER_NODE = event.registerNode(GameClient.ID, "drawScene");
        DRAW_FBO_SCENE_RENDER_NODE = event.registerNode(GameClient.ID, "drawFboScene");
        DRAW_GBUFFER_NODE = event.registerNode(GameClient.ID, "drawGBuffer");
        DRAW_DEBUG_GBUFFER_NODE = event.registerNode(GameClient.ID, "drawDebugGBuffer");
        DRAW_UI_RENDER_NODE = event.registerNode(GameClient.ID, "drawUI");

        //Routines
        UPDATE_ROTATIONS_ROUTINE_NODE = event.registerNode(GameClient.ID, "updateRotations");
        //Routes
        BOOLEAN_ROUTE = event.registerRoute(GameClient.ID, "booleanRoute");
        DEBUG_ROUTE = event.registerRoute(GameClient.ID, "debugRoute");
        GBUFFER_DEBUG_ROUTE = event.registerRoute(GameClient.ID, "gbufferDebugRoute");
        SCENE_FBO_DEBUG_ROUTE = event.registerRoute(GameClient.ID, "sceneFboDebugRoute");

        var rotateSystemRoute = RenderGraph.RenderPath.builder().addRoutineNode(GameRoutines.DEFAULT_ROUTINE);

        var mainSceneRoute = RenderGraph.RenderPath.builder()
                .addRenderNode(DRAW_SCENE_RENDER_NODE, DrawSceneRenderNode.class, GameShaders.MESH_SHADER_PROGRAM_CONFIG);

        var debugGBufferRoute = RenderGraph.RenderPath.builder()
                .setTarget(GBUFFER_FBO_ID)
                .addRenderNode(DRAW_GBUFFER_NODE, GBufferRenderNode.class, GameShaders.GBUFFER_SHADER_PROGRAM_CONFIG)
                .setTarget(null)
                .addRenderNode(DRAW_DEBUG_GBUFFER_NODE, DebugGBufferRenderNode.class, GameShaders.DEBUG_QUAD_SHADER_PROGRAM_CONFIG);

        var fboSceneRoute = RenderGraph.RenderPath.builder()
                .setTarget(SCENE_FBO_ID)
                .addRenderNode(DRAW_FBO_SCENE_RENDER_NODE, DrawSceneRenderNode.class, GameShaders.MESH_SHADER_PROGRAM_CONFIG)
                .setTarget(null);

        DEFAULT_RENDER_GRAPH = event.registerGraph(GameClient.ID, "draw_scene",
                new RenderGraph(RenderGraph.RenderPath.builder()
                        .route(
                                BOOLEAN_ROUTE,
                                (__, sh) -> (boolean) sh.getState(GameStates.ROTATE_ENTITIES).getValue(),
                                rotateSystemRoute)
                        .route(
                                DEBUG_ROUTE,
                                (__, sh) -> sh.getState(GameStates.DEBUG_RENDERER).getValue() == GameStates.CurrentRenderer.SCENE,
                                mainSceneRoute,
                                RenderGraph.RenderPath.builder()
                                        .route(
                                                GBUFFER_DEBUG_ROUTE,
                                                (__, sh) -> sh.getState(GameStates.DEBUG_RENDERER).getValue() == GameStates.CurrentRenderer.GBUFFER,
                                                debugGBufferRoute,
                                                fboSceneRoute
                                        )
                        )
                        .addRenderNode(DRAW_UI_RENDER_NODE, DrawTestTWUIRenderNode.class, ShaderProgramConfig.EMPTY)
                        .configure(PRINT_ON_EXECUTE, false)
                )
        );
    }

}

