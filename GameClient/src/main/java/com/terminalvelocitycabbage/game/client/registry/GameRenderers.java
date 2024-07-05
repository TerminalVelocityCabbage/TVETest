package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.RenderGraph;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Shader;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Uniform;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawSceneRenderNode;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

public class GameRenderers {

    public static Identifier DEFAULT_RENDER_GRAPH;

    public static void init(GameClient client) {
        //Build Render Graphs
        RenderGraph sceneRenderGraph = RenderGraph.builder()
                .shaderProgram(ShaderProgramConfig.builder()
                        .vertexFormat(MESH_FORMAT)
                        .addShader(Shader.Type.VERTEX, GameShaders.TEST_VERTEX_SHADER)
                        .addShader(Shader.Type.FRAGMENT, GameShaders.TEST_FRAGMENT_SHADER)
                        .addUniform(new Uniform("textureSampler"))
                        .build()
                )
                .addNode(client.identifierOf("drawScene"), DrawSceneRenderNode.class)
                .build();

        DEFAULT_RENDER_GRAPH = client.identifierOf("scene");
        //Register renderers
        client.getRenderGraphRegistry().register(DEFAULT_RENDER_GRAPH, sceneRenderGraph);;
    }

}

