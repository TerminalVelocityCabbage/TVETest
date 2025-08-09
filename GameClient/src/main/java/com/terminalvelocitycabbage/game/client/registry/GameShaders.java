package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.shader.Shader;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.renderer.shader.Uniform;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.rendernodes.DrawUIRenderNode;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

public class GameShaders {

    public static Identifier DEFAULT_VERTEX_SHADER;
    public static Identifier DEFAULT_FRAGMENT_SHADER;
    public static Identifier UI_VERTEX_SHADER;
    public static Identifier UI_FRAGMENT_SHADER;
    public static ShaderProgramConfig MESH_SHADER_PROGRAM_CONFIG;
    public static ShaderProgramConfig UI_SHADER_PROGRAM_CONFIG;

    public static void init(ResourceRegistrationEvent event) {
        DEFAULT_VERTEX_SHADER = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.SHADER, "default.vert").getIdentifier();
        DEFAULT_FRAGMENT_SHADER = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.SHADER, "default.frag").getIdentifier();
        UI_VERTEX_SHADER = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.SHADER, "ui.vert").getIdentifier();
        UI_FRAGMENT_SHADER = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.SHADER, "ui.frag").getIdentifier();

        //TODO separate event for shader resources and program configuration
        MESH_SHADER_PROGRAM_CONFIG = ShaderProgramConfig.builder()
                .vertexFormat(MESH_FORMAT)
                .addShader(Shader.Type.VERTEX, GameShaders.DEFAULT_VERTEX_SHADER)
                .addShader(Shader.Type.FRAGMENT, GameShaders.DEFAULT_FRAGMENT_SHADER)
                .addUniform(new Uniform("textureSampler"))
                .addUniform(new Uniform("projectionMatrix"))
                .addUniform(new Uniform("viewMatrix"))
                .addUniform(new Uniform("modelMatrix"))
                .build();
        UI_SHADER_PROGRAM_CONFIG = ShaderProgramConfig.builder()
                .vertexFormat(DrawUIRenderNode.UI_ELEMENT_MESH_FORMAT)
                .addShader(Shader.Type.VERTEX, GameShaders.UI_VERTEX_SHADER)
                .addShader(Shader.Type.FRAGMENT, GameShaders.UI_FRAGMENT_SHADER)
                .addUniform(new Uniform("textureSampler"))
                .addUniform(new Uniform("projectionMatrix"))
                .addUniform(new Uniform("modelMatrix"))
                .build();
    }

}
