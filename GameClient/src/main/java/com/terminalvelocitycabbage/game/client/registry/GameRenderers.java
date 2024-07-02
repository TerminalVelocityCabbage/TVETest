package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.RenderGraph;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Shader;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Uniform;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawSceneRenderNode;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

public class GameRenderers {

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

        //Register renderers
        client.getRenderGraphRegistry().register(client.identifierOf("scene"), sceneRenderGraph);
    }

}

