package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.ui.UIRenderNode;
import com.terminalvelocitycabbage.engine.client.ui.data.*;
import com.terminalvelocitycabbage.engine.client.ui.data.configs.LayoutConfig;
import com.terminalvelocitycabbage.engine.client.ui.data.configs.TextElementConfig;
import com.terminalvelocitycabbage.engine.util.Color;

import com.terminalvelocitycabbage.game.client.registry.GameFonts;

public class DrawTestUIRenderNode extends UIRenderNode {

    public DrawTestUIRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    @Override
    protected void declareUI() {
        container(
                ElementDeclaration.builder()
                        .backgroundColor(new Color(255, 255, 255, 100))
                        .layout(LayoutConfig.builder()
                                .sizing(Sizing.grow())
                                .padding(new Padding().top(50).left(50))
                                .build())
                        .build(), () -> {
                    text("Hello World", TextElementConfig.builder()
                            .fontSize(32)
                            .textColor(new Color(0, 0, 0, 255))
                            .fontIdentifier(GameFonts.UCHRONY_FONT).build()
                    );
        });
    }
}
