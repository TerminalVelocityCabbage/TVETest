package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.RenderGraph;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawSceneRenderNode;
import com.terminalvelocitycabbage.templates.events.RendererRegistrationEvent;

public class GameRenderers {

    //Render Node Identifiers
    public static Identifier DRAW_SCENE_RENDER_NODE = new Identifier(GameClient.ID, "drawScene");
    //Render Graph Identifiers
    public static Identifier DEFAULT_RENDER_GRAPH;

    public static void init(RendererRegistrationEvent event) {
        DEFAULT_RENDER_GRAPH = event.register(new Identifier(GameClient.ID, "drawScene"),
                RenderGraph.builder()
                        .addRoutineNode(new Identifier(GameClient.ID, "updateRotations"), GameRoutines.DEFAULT_ROUTINE)
                        .addRenderNode(DRAW_SCENE_RENDER_NODE, DrawSceneRenderNode.class, GameShaders.MESH_SHADER_PROGRAM_CONFIG)
                        .build()
        ).getIdentifier();
    }

}

