package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.TargetProperties;
import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgram;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.HeterogeneousMap;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class DebugGBufferRenderNode extends RenderNode {

    private Mesh quad;

    public DebugGBufferRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    @Override
    public void render(Scene scene, TargetProperties properties, HeterogeneousMap renderConfig, long deltaTime) {

        if (quad == null) quad = Mesh.createQuad(getShaderProgram().getConfig().getVertexFormat());

        // Clear depth so quads don't fight with each other or previous depth
        glClear(GL_DEPTH_BUFFER_BIT);

        var shader = getShaderProgram();
        shader.bind();

        // Position: Top Left
        renderQuad(shader, GameTextures.POSITION_TEXTURE, -0.5f, 0.5f, false);
        // Normals: Top Right
        renderQuad(shader, GameTextures.NORMALS_TEXTURE, 0.5f, 0.5f, false);
        // Albedo: Bottom Left
        renderQuad(shader, GameTextures.ALBEDO_TEXTURE, -0.5f, -0.5f, false);
        // Depth: Bottom Right
        renderQuad(shader, GameTextures.DEPTH_TEXTURE, 0.5f, -0.5f, true);

        shader.unbind();
    }

    private void renderQuad(ShaderProgram shader, Identifier textureId, float x, float y, boolean isDepth) {
        Matrix4f modelMatrix = new Matrix4f().translation(x, y, 0).scale(0.5f);
        shader.getUniform("modelMatrix").setUniform(modelMatrix);
        shader.getUniform("isDepth").setUniform(isDepth);
        var texture = GameClient.getInstance().getTextureCache().getTexture(textureId);
        if (texture != null) {
            texture.bind();
            quad.render();
        }
    }
}
