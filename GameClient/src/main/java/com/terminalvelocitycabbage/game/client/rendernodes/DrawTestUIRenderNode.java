package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.ui.UI;
import com.terminalvelocitycabbage.engine.client.ui.UIElement;
import com.terminalvelocitycabbage.engine.client.ui.UIRenderNode;
import com.terminalvelocitycabbage.engine.client.ui.data.*;
import com.terminalvelocitycabbage.engine.client.ui.data.configs.AspectRatioElementConfig;
import com.terminalvelocitycabbage.engine.client.ui.data.configs.FloatingElementConfig;
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
                                .sizing(new Sizing(SizingAxis.grow(), SizingAxis.percent(50)))
                                .padding(new Padding().top(50).left(50))
                                .build())
                        .build(), () -> {
                    text("Hello World", TextElementConfig.builder()
                            .fontSize(32)
                            .textColor(new Color(0, 0, 0, 255))
                            .fontIdentifier(GameFonts.UCHRONY_FONT).build()
                    );
                    floatingElementTest();
                    aspectRatioTest();
        });
    }

    private UIElement floatingElementTest() {
        return container(
                ElementDeclaration.builder()
                        .backgroundColor(new Color(0, 0, 255, 100))
                        .floating(FloatingElementConfig.builder()
                                .attachPoints(new FloatingAttachPoints(UI.FloatingAttachPointType.LEFT, UI.FloatingAttachPointType.LEFT))
                                .attachTo(UI.FloatingAttachToElement.ELEMENT_WITH_ID, id("aspect"))
                                .build())
                        .layout(LayoutConfig.builder()
                                .sizing(new Sizing(SizingAxis.fixed(100f), SizingAxis.fixed(100f)))
                                .build())
                        .build(),
                () -> {}
        );
    }

    private UIElement aspectRatioTest() {
        return container(id("aspect"),
                ElementDeclaration.builder()
                        .backgroundColor(new Color(255, 0, 0, 100))
                        .aspectRatio(new AspectRatioElementConfig(2.0f))
                        .layout(LayoutConfig.builder()
                                .sizing(new Sizing(SizingAxis.fit(), SizingAxis.fit()))
                                .build())
                        .build(),
                () -> {
                    text("Aspect Ratio 2:1", TextElementConfig.builder()
                            .fontSize(24)
                            .textColor(new Color(255, 255, 255, 255))
                            .fontIdentifier(GameFonts.UCHRONY_FONT).build());
                }
        );
    }
}
