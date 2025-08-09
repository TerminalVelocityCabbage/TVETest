package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.client.ui.Style;
import com.terminalvelocitycabbage.engine.graph.UIRenderNode;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;

public class DrawUIRenderNode extends UIRenderNode {

    public DrawUIRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    @Override
    public void drawUIElements(Scene scene) {

        drawBox(scene, Style.builder().setTexture(GameTextures.HAPPY).setScale(100).build());

    }
}
