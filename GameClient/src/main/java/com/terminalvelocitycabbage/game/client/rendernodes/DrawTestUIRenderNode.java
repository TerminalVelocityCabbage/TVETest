package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.ui.UIRenderNode;
import com.terminalvelocitycabbage.engine.client.ui.data.ElementDeclaration;
import com.terminalvelocitycabbage.engine.client.ui.data.TextElementConfig;

public class DrawTestUIRenderNode extends UIRenderNode {


    public DrawTestUIRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    @Override
    protected void declareUI() {
        container(ElementDeclaration.builder().build(), () -> {
            text("Hello World", TextElementConfig.builder().fontSize(32).build());
        });
    }
}
