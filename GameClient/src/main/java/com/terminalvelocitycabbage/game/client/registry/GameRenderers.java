package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.RenderGraph;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Shader;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Uniform;
import com.terminalvelocitycabbage.engine.registry.Registry;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawSceneRenderNode;
import com.terminalvelocitycabbage.game.client.rendernodes.OppositeSpinningSquareRenderNode;
import com.terminalvelocitycabbage.game.client.rendernodes.SpinningSquareRenderNode;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

public class GameRenderers {

    public static void init(GameClient client, Registry<RenderGraph> renderGraphRegistry) {
        //Build Render Graphs
        RenderGraph spinningSquareRenderGraph = RenderGraph.builder()
                .addNode(client.identifierOf("spinningSquare"), SpinningSquareRenderNode.class)
                .build();
        RenderGraph oppositeSpinningSquareRenderGraph = RenderGraph.builder()
                .addNode(client.identifierOf("oppositeSpinningSquare"), OppositeSpinningSquareRenderNode.class)
                .build();
        RenderGraph sceneRenderGraph = RenderGraph.builder()
                .shaderProgram(ShaderProgramConfig.builder()
                        .vertexFormat(MESH_FORMAT)
                        .addShader(Shader.Type.VERTEX, client.identifierOf("default.vert"))
                        .addShader(Shader.Type.FRAGMENT, client.identifierOf("default.frag"))
                        .addUniform(new Uniform("textureSampler"))
                        .build()
                )
                .addNode(client.identifierOf("drawScene"), DrawSceneRenderNode.class)
                .build();

        //Register renderers
        renderGraphRegistry.register(client.identifierOf("game"), spinningSquareRenderGraph);
        renderGraphRegistry.register(client.identifierOf("game2"), oppositeSpinningSquareRenderGraph);
        renderGraphRegistry.register(client.identifierOf("scene"), sceneRenderGraph);
    }

}

