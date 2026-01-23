package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.ui.UIRenderNode;
import com.terminalvelocitycabbage.engine.client.ui.data.ElementDeclaration;
import com.terminalvelocitycabbage.engine.client.ui.data.TextElementConfig;
import com.terminalvelocitycabbage.engine.util.Color;

public class DrawTestUIRenderNode extends UIRenderNode {

    public DrawTestUIRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    @Override
    protected void declareUI() {
        container(ElementDeclaration.builder()
                .backgroundColor(new Color(255, 255, 255, 255)).build(), () -> {
            text("Hello World", TextElementConfig.builder().fontSize(32).build());
        });
    }
}
