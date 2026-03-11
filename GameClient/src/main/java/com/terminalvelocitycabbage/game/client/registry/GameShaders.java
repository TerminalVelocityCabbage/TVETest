package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.shader.Shader;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Uniform;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

import static com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory.SHADER;
import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

public class GameShaders {

    public static final Identifier TEST_VERTEX_SHADER = SHADER.identifierOf(GameClient.ID, "default_vertex");
    public static final Identifier TEST_FRAGMENT_SHADER = SHADER.identifierOf(GameClient.ID, "default_fragment");
    public static final ShaderProgramConfig MESH_SHADER_PROGRAM_CONFIG = ShaderProgramConfig.builder()
            .vertexFormat(MESH_FORMAT)
            .addShader(Shader.Type.VERTEX, GameShaders.TEST_VERTEX_SHADER)
            .addShader(Shader.Type.FRAGMENT, GameShaders.TEST_FRAGMENT_SHADER)
            .addUniform(new Uniform("textureSampler"))
            .addUniform(new Uniform("projectionMatrix"))
            .addUniform(new Uniform("viewMatrix"))
            .addUniform(new Uniform("modelMatrix"))
            .build();

}
