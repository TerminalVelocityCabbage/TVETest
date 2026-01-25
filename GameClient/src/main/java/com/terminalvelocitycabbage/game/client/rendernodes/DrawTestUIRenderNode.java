package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.ui.UI;
import com.terminalvelocitycabbage.engine.client.ui.UIElement;
import com.terminalvelocitycabbage.engine.client.ui.UIRenderNode;
import com.terminalvelocitycabbage.engine.client.ui.data.*;
import com.terminalvelocitycabbage.engine.client.ui.data.configs.*;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.Color;
import com.terminalvelocitycabbage.game.client.registry.GameFonts;
import com.terminalvelocitycabbage.templates.events.UIClickEvent;
import com.terminalvelocitycabbage.templates.events.UIScrollEvent;

public class DrawTestUIRenderNode extends UIRenderNode {

    public DrawTestUIRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    @Override
    protected Identifier[] getInterestedEvents() {
        return new Identifier[] { UIClickEvent.EVENT, UIScrollEvent.EVENT };
    }

    @Override
    protected void declareUI() {
        container(
                ElementDeclaration.builder()
                        .backgroundColor(new Color(255, 255, 255, 100))
                        .layout(LayoutConfig.builder()
                                .sizing(new Sizing(SizingAxis.grow(), SizingAxis.percent(50)))
                                .padding(new Padding().top(10).left(15))
                                .wrap(true)
                                .build())
                        .build(), () -> {
                    buttonTest();
                    stateTest();
                    floatingElementTest();
                    aspectRatioTest();
                    textWrapTest();
                    wrapTest();
                    borderAndCornersTest();
        });
    }

    private void buttonTest() {
        int buttonId = id("testButton");
        boolean hovered = isHovered(buttonId);

        if (heardEvent(buttonId, UIClickEvent.EVENT) instanceof UIClickEvent event) {
            Log.info("Button CLICKED at " + event.getPosition() + "!");
        }

        container(buttonId, ElementDeclaration.builder()
                .backgroundColor(hovered ? new Color(150, 150, 150, 255) : new Color(100, 100, 100, 255))
                .cornerRadius(new CornerRadius(10))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fixed(150f), SizingAxis.fixed(50f)))
                        .childAlignment(new ChildAlignment(UI.HorizontalAlignment.CENTER, UI.VerticalAlignment.CENTER))
                        .build())
                .build(), () -> {
            text("Click Me", TextElementConfig.builder()
                    .fontSize(20)
                    .textColor(new Color(255, 255, 255, 255))
                    .fontIdentifier(GameFonts.UCHRONY_FONT)
                    .build());
        });
    }

    private void stateTest() {
        int buttonId = id("stateButton");
        var clickCount = useState(0);

        if (heardEvent(buttonId, UIClickEvent.EVENT) instanceof UIClickEvent) {
            clickCount.setValue(clickCount.getValue() + 1);
        }

        container(buttonId, ElementDeclaration.builder()
                .backgroundColor(isHovered(buttonId) ? new Color(150, 150, 150, 255) : new Color(100, 100, 100, 255))
                .cornerRadius(new CornerRadius(10))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fixed(200f), SizingAxis.fixed(50f)))
                        .childAlignment(new ChildAlignment(UI.HorizontalAlignment.CENTER, UI.VerticalAlignment.CENTER))
                        .build())
                .build(), () -> {
            text("Clicked " + clickCount.getValue() + " times", TextElementConfig.builder()
                    .fontSize(20)
                    .textColor(new Color(255, 255, 255, 255))
                    .fontIdentifier(GameFonts.UCHRONY_FONT)
                    .build());
        });
    }

    private void borderAndCornersTest() {
        container(ElementDeclaration.builder()
                .backgroundColor(new Color(0, 0, 0, 50))
                .cornerRadius(new CornerRadius((float) (20f * Math.sin(ClientBase.getInstance().getRuntime() / 1000f) + 20f), 0, 20, 0))
                .border(BorderElementConfig.builder()
                        .color(new Color(255, 255, 255, 255))
                        .width(new BorderWidth((int) (5 * Math.sin(ClientBase.getInstance().getRuntime() / 1500f) + 5)))
                        .build())
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fixed(200f), SizingAxis.fixed(100f)))
                        .padding(new Padding(10))
                        .build())
                .build(), () -> {
            text("Border & Corners", TextElementConfig.builder()
                    .fontSize(20)
                    .textColor(new Color(255, 255, 255, 255))
                    .fontIdentifier(GameFonts.UCHRONY_FONT)
                    .build());
        });
    }

    private void textWrapTest() {
        container(ElementDeclaration.builder()
                .backgroundColor(new Color(255, 255, 0, 50))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.percent(30), SizingAxis.fit()))
                        .padding(new Padding(10))
                        .build())
                .build(), () -> {
            text("This is a long piece of text that should wrap because it is inside a container with a fixed width of 200 pixels. We are testing if the layout engine correctly calculates the height of the text after wrapping.",
                    TextElementConfig.builder()
                            .fontSize(16)
                            .textColor(new Color(0, 0, 0, 255))
                            .fontIdentifier(GameFonts.UCHRONY_FONT)
                            .wrapMode(UI.TextWrapMode.WORDS)
                            .textAlignment(UI.TextAlignment.LEFT)
                            .lineHeight(10)
                            .build());
        });
    }

    private void wrapTest() {
        container(ElementDeclaration.builder()
                .backgroundColor(new Color(0, 255, 0, 50))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.percent(25), SizingAxis.fit()))
                        .childGap(10)
                        .padding(new Padding(10))
                        .wrap(true)
                        .build())
                .build(), () -> {
            for (int i = 0; i < 10; i++) {
                final int index = i;
                container(ElementDeclaration.builder()
                        .backgroundColor(new Color(255, 0, 0, 150))
                        .layout(LayoutConfig.builder()
                                .sizing(new Sizing(SizingAxis.fixed(80f), SizingAxis.fixed(40f)))
                                .build())
                        .build(), () -> {
                    text("Item " + index, TextElementConfig.builder()
                            .fontSize(16)
                            .textColor(new Color(255, 255, 255, 255))
                            .fontIdentifier(GameFonts.UCHRONY_FONT).build());
                });
            }
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
