package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.shader.Shader;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Uniform;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

public class GameShaders {

    public static Identifier TEST_VERTEX_SHADER;
    public static Identifier TEST_FRAGMENT_SHADER;
    public static ShaderProgramConfig MESH_SHADER_PROGRAM_CONFIG;

    public static void init(ResourceRegistrationEvent event) {
        TEST_VERTEX_SHADER = event.registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceCategory.SHADER, "default.vert").getIdentifier();
        TEST_FRAGMENT_SHADER = event.registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceCategory.SHADER, "default.frag").getIdentifier();
        MESH_SHADER_PROGRAM_CONFIG = ShaderProgramConfig.builder()
                        .vertexFormat(MESH_FORMAT)
                        .addShader(Shader.Type.VERTEX, GameShaders.TEST_VERTEX_SHADER)
                        .addShader(Shader.Type.FRAGMENT, GameShaders.TEST_FRAGMENT_SHADER)
                        .addUniform(new Uniform("textureSampler"))
                        .addUniform(new Uniform("projectionMatrix"))
                        .addUniform(new Uniform("viewMatrix"))
                        .addUniform(new Uniform("modelMatrix"))
                        .build();
    }

}
