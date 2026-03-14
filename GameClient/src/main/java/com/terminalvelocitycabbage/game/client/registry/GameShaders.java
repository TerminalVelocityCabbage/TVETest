package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.shader.Shader;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Uniform;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

import static com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory.SHADER;
import static com.terminalvelocitycabbage.game.client.data.MeshData.DEBUG_QUAD_FORMAT;
import static com.terminalvelocitycabbage.game.client.data.MeshData.GBUFFER_FORMAT;
import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

public class GameShaders {

    public static final Identifier TEST_VERTEX_SHADER = SHADER.identifierOf(GameClient.ID, "default_vertex.vert");
    public static final Identifier TEST_FRAGMENT_SHADER = SHADER.identifierOf(GameClient.ID, "default_fragment.frag");
    public static final Identifier GBUFFER_VERTEX_SHADER = SHADER.identifierOf(GameClient.ID, "gbuffer.vert");
    public static final Identifier GBUFFER_FRAGMENT_SHADER = SHADER.identifierOf(GameClient.ID, "gbuffer.frag");
    public static final Identifier DEBUG_QUAD_VERTEX_SHADER = SHADER.identifierOf(GameClient.ID, "debug_quad.vert");
    public static final Identifier DEBUG_QUAD_FRAGMENT_SHADER = SHADER.identifierOf(GameClient.ID, "debug_quad.frag");

    public static final ShaderProgramConfig MESH_SHADER_PROGRAM_CONFIG = ShaderProgramConfig.builder()
            .vertexFormat(MESH_FORMAT)
            .addShader(Shader.Type.VERTEX, TEST_VERTEX_SHADER)
            .addShader(Shader.Type.FRAGMENT, TEST_FRAGMENT_SHADER)
            .addUniform(new Uniform("textureSampler"))
            .addUniform(new Uniform("projectionMatrix"))
            .addUniform(new Uniform("viewMatrix"))
            .addUniform(new Uniform("modelMatrix"))
            .build();

    public static final ShaderProgramConfig GBUFFER_SHADER_PROGRAM_CONFIG = ShaderProgramConfig.builder()
            .vertexFormat(GBUFFER_FORMAT)
            .addShader(Shader.Type.VERTEX, GBUFFER_VERTEX_SHADER)
            .addShader(Shader.Type.FRAGMENT, GBUFFER_FRAGMENT_SHADER)
            .addUniform(new Uniform("textureSampler"))
            .addUniform(new Uniform("projectionMatrix"))
            .addUniform(new Uniform("viewMatrix"))
            .addUniform(new Uniform("modelMatrix"))
            .build();

    public static final ShaderProgramConfig DEBUG_QUAD_SHADER_PROGRAM_CONFIG = ShaderProgramConfig.builder()
            .vertexFormat(DEBUG_QUAD_FORMAT)
            .addShader(Shader.Type.VERTEX, DEBUG_QUAD_VERTEX_SHADER)
            .addShader(Shader.Type.FRAGMENT, DEBUG_QUAD_FRAGMENT_SHADER)
            .addUniform(new Uniform("screenTexture"))
            .addUniform(new Uniform("modelMatrix"))
            .addUniform(new Uniform("isDepth"))
            .build();

}
