package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.ui.UI;
import com.terminalvelocitycabbage.engine.client.ui.UIElement;
import com.terminalvelocitycabbage.engine.client.ui.UIRenderNode;
import com.terminalvelocitycabbage.engine.client.ui.data.*;
import com.terminalvelocitycabbage.engine.client.ui.data.configs.*;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.Color;
import com.terminalvelocitycabbage.game.client.registry.GameFonts;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;
import com.terminalvelocitycabbage.templates.events.UIClickEvent;
import com.terminalvelocitycabbage.templates.events.UIScrollEvent;
import org.joml.Vector2f;

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
                                .sizing(new Sizing(SizingAxis.grow(), SizingAxis.grow()))
                                .padding(new Padding().top(10).left(15)))
                        .build(), () -> {
                    stateTest();
                    scrollTest();
                    floatingElementTest();
                    aspectRatioTest();
                    reverseLayoutTest();
                    textWrapTest();
                    wrapTest();
                    borderAndCornersTest();
                    zIndexAndCaptureTest();
                    marginTest();
                    imageTest();
        });
    }

    private void imageTest() {
        container(id("imageContainer"), ElementDeclaration.builder()
                .backgroundColor(new Color(255, 255, 255, 50))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fit(), SizingAxis.fit()))
                        .layoutDirection(UI.LayoutDirection.LEFT_TO_RIGHT)
                        .childGap(10)
                        .padding(new Padding(10)))
                .build(), () -> {
            // Natural size image
            image(id("naturalSize"), ElementDeclaration.builder()
                    .border(new BorderElementConfig(new Color(255, 255, 255, 255), new BorderWidth(2)))
                    .image(ImageElementConfig.builder()
                            .imageIdentifier(GameTextures.HAPPY)
                            .atlasIdentifier(GameTextures.UI_TEXTURE_ATLAS))
                    .build());

            // Fit height, fixed width
            image(id("fixedWidthFitHeight"), ElementDeclaration.builder()
                    .border(new BorderElementConfig(new Color(255, 0, 0, 255), new BorderWidth(2)))
                    .layout(LayoutConfig.builder()
                            .aspectRatio(1)
                            .sizing(new Sizing(SizingAxis.fixed(100), SizingAxis.fit())))
                    .image(ImageElementConfig.builder()
                            .imageIdentifier(GameTextures.HAPPY)
                            .atlasIdentifier(GameTextures.UI_TEXTURE_ATLAS))
                    .build());

            // Fit width, fixed height
            image(id("fitWidthFixedHeight"), ElementDeclaration.builder()
                    .border(new BorderElementConfig(new Color(0, 255, 0, 255), new BorderWidth(2)))
                    .layout(LayoutConfig.builder()
                            .sizing(new Sizing(SizingAxis.fit(), SizingAxis.fixed(100)))
                            .aspectRatio(1))
                    .image(ImageElementConfig.builder()
                            .imageIdentifier(GameTextures.HAPPY)
                            .atlasIdentifier(GameTextures.UI_TEXTURE_ATLAS))
                    .build());

            // Fixed both (squished)
            image(id("fixedBoth"), ElementDeclaration.builder()
                    .border(new BorderElementConfig(new Color(0, 0, 255, 255), new BorderWidth(2)))
                    .layout(LayoutConfig.builder()
                            .sizing(new Sizing(SizingAxis.fixed(150), SizingAxis.fixed(50))))
                    .image(ImageElementConfig.builder()
                            .imageIdentifier(GameTextures.HAPPY)
                            .atlasIdentifier(GameTextures.UI_TEXTURE_ATLAS))
                    .build());
        });
    }

    private void marginTest() {
        container(id("marginContainer"), ElementDeclaration.builder()
                .backgroundColor(new Color(100, 100, 255, 255))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fit(), SizingAxis.fit()))
                        .layoutDirection(UI.LayoutDirection.LEFT_TO_RIGHT))
                .build(), () -> {
            // Child with margin
            container(id("marginChild1"), ElementDeclaration.builder()
                    .backgroundColor(new Color(255, 100, 100, 255))
                    .layout(LayoutConfig.builder()
                            .sizing(new Sizing(SizingAxis.fixed(50), SizingAxis.fixed(50)))
                            .margin(new Margin().left(20).top(10).bottom(5).right(10)))
                    .build(), () -> {});

            // Child without margin
            container(id("marginChild2"), ElementDeclaration.builder()
                    .backgroundColor(new Color(100, 255, 100, 255))
                    .layout(LayoutConfig.builder()
                            .sizing(new Sizing(SizingAxis.fixed(50), SizingAxis.fixed(50))))
                    .build(), () -> {});
        });
    }

    private void zIndexAndCaptureTest() {
        int backId = id("zIndexBack");
        int frontId = id("zIndexFront");
        int passId = id("zIndexPass");

        var backClicks = useState("backClicks", 0);
        var frontClicks = useState("frontClicks", 0);
        var passClicks = useState("passClicks", 0);

        if (heardEvent(backId, UIClickEvent.EVENT) != null) backClicks.setValue(backClicks.getValue() + 1);
        if (heardEvent(frontId, UIClickEvent.EVENT) != null) frontClicks.setValue(frontClicks.getValue() + 1);
        if (heardEvent(passId, UIClickEvent.EVENT) != null) passClicks.setValue(passClicks.getValue() + 1);

        // Background floating element (lower zIndex)
        container(backId, ElementDeclaration.builder()
                .backgroundColor(isHovered(backId) ? new Color(255, 100, 100, 255) : new Color(200, 0, 0, 255))
                .floating(FloatingElementConfig.builder()
                        .zIndex(10)
                        .offset(new Vector2f(50, 450))
                        .attachTo(UI.FloatingAttachToElement.ROOT))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fixed(200f), SizingAxis.fixed(200f)))
                        .childAlignment(new ChildAlignment(UI.HorizontalAlignment.CENTER, UI.VerticalAlignment.CENTER)))
                .build(), () -> {
            text("z-index: 10\nClicks: " + backClicks.getValue(), TextElementConfig.builder().fontSize(20).textColor(new Color(255, 255, 255, 255)).fontIdentifier(GameFonts.LEXEND_FONT).build());
        });

        // Foreground floating element (higher zIndex, partially overlapping)
        container(frontId, ElementDeclaration.builder()
                .backgroundColor(isHovered(frontId) ? new Color(100, 255, 100, 255) : new Color(0, 200, 0, 255))
                .floating(FloatingElementConfig.builder()
                        .zIndex(20)
                        .offset(new Vector2f(150, 550))
                        .attachTo(UI.FloatingAttachToElement.ROOT)
                        .pointerCaptureMode(UI.PointerCaptureMode.CAPTURE))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fixed(200f), SizingAxis.fixed(200f)))
                        .childAlignment(new ChildAlignment(UI.HorizontalAlignment.CENTER, UI.VerticalAlignment.CENTER)))
                .build(), () -> {
            text("z-index: 20\nCapture\nClicks: " + frontClicks.getValue(), TextElementConfig.builder().fontSize(20).textColor(new Color(255, 255, 255, 255)).fontIdentifier(GameFonts.LEXEND_FONT).build());
        });

        // Passthrough floating element (even higher zIndex, but passthrough)
        container(passId, ElementDeclaration.builder()
                .backgroundColor(isHovered(passId) ? new Color(100, 100, 255, 150) : new Color(0, 0, 200, 100))
                .floating(FloatingElementConfig.builder()
                        .zIndex(30)
                        .offset(new Vector2f(250, 450))
                        .attachTo(UI.FloatingAttachToElement.ROOT)
                        .pointerCaptureMode(UI.PointerCaptureMode.PASSTHROUGH))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fixed(150f), SizingAxis.fixed(150f)))
                        .childAlignment(new ChildAlignment(UI.HorizontalAlignment.CENTER, UI.VerticalAlignment.CENTER)))
                .build(), () -> {
            text("z-index: 30\nPassthrough\nClicks: " + passClicks.getValue(), TextElementConfig.builder().fontSize(20).textColor(new Color(255, 255, 255, 255)).fontIdentifier(GameFonts.LEXEND_FONT).build());
        });
    }

    private void scrollTest() {
        scrollableContainer("myScrollable",
                ElementDeclaration.builder()
                        .backgroundColor(new Color(50, 50, 50, 255))
                        .layout(LayoutConfig.builder()
                                .layoutDirection(UI.LayoutDirection.TOP_TO_BOTTOM)
                                .sizing(new Sizing(SizingAxis.fixed(200f), SizingAxis.grow()))
                                .padding(new Padding(10))
                                .childGap(5))
                        .build(),
                ElementDeclaration.builder()
                        .backgroundColor(new Color(255, 0, 0, 255))
                        .layout(LayoutConfig.builder()
                                .sizing(new Sizing(SizingAxis.fixed(10f), SizingAxis.fit())))
                        .build(),
                () -> {
                    for (int i = 0; i < 20; i++) {
                        int index = i;
                        container(ElementDeclaration.builder()
                                .backgroundColor(new Color(100, 100, 255, 255))
                                .layout(LayoutConfig.builder()
                                        .sizing(new Sizing(SizingAxis.fixed(50), SizingAxis.fixed(30f)))
                                        .childAlignment(new ChildAlignment(UI.HorizontalAlignment.CENTER, UI.VerticalAlignment.CENTER)))
                                .build(), () -> {
                            text("Item " + index, TextElementConfig.builder()
                                    .fontIdentifier(GameFonts.LEXEND_FONT)
                                    .textColor(new Color(255, 255, 255, 255))
                                    .build());
                        });
                    }
                }
        );
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
                        .childAlignment(new ChildAlignment(UI.HorizontalAlignment.CENTER, UI.VerticalAlignment.CENTER)))
                .build(), () -> {
            text("Clicked " + clickCount.getValue() + " times", TextElementConfig.builder()
                    .fontSize(20)
                    .textColor(new Color(255, 255, 255, 255))
                    .fontIdentifier(GameFonts.LEXEND_FONT)
                    .build());
        });
    }

    private void borderAndCornersTest() {
        container(ElementDeclaration.builder()
                .backgroundColor(new Color(0, 0, 0, 50))
                .cornerRadius(new CornerRadius((float) (20f * Math.sin(ClientBase.getInstance().getRuntime() / 1000f) + 20f), 0, 20, 0))
                .border(new BorderElementConfig(
                        new Color(255, 255, 255, 255),
                        new BorderWidth((int) (5 * Math.sin(ClientBase.getInstance().getRuntime() / 1500f) + 5))
                ))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fixed(200f), SizingAxis.fixed(100f)))
                        .padding(new Padding(10)))
                .build(), () -> {
            text("Border & Corners", TextElementConfig.builder()
                    .fontSize(20)
                    .textColor(new Color(255, 255, 255, 255))
                    .fontIdentifier(GameFonts.LEXEND_FONT)
                    .build());
        });
    }

    private void textWrapTest() {
        container(ElementDeclaration.builder()
                .backgroundColor(new Color(255, 255, 0, 50))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.percent(30), SizingAxis.fit()))
                        .padding(new Padding(10)))
                .build(), () -> {
            text("This is a long piece of text that should wrap because it is inside a container with a fixed width of 200 pixels. We are testing if the layout engine correctly calculates the height of the text after wrapping.",
                    TextElementConfig.builder()
                            .fontSize(16)
                            .textColor(new Color(0, 0, 0, 255))
                            .fontIdentifier(GameFonts.LEXEND_FONT)
                            .wrapMode(UI.TextWrapMode.WORDS)
                            .textAlignment(UI.TextAlignment.LEFT)
                            .lineHeight(10)
                            .build());
        });
    }

    private void wrapTest() {
        container("bg-[0,1,0,0.2] w-[25%] h-fit gap-[10] p-[10] wrap", () -> {
            for (int i = 0; i < 10; i++) {
                final int index = i;
                container("bg-[1,0,0,0.6] w-[80px] h-[40px]", () -> {
                    text("Item " + index, "text-size-[16] text-color-[1,1,1] font-[" + GameFonts.LEXEND_FONT + "]");
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
                                .attachTo(UI.FloatingAttachToElement.ELEMENT_WITH_ID, id("aspect")))
                        .layout(LayoutConfig.builder()
                                .sizing(new Sizing(SizingAxis.fixed(100f), SizingAxis.fixed(100f))))
                        .build(),
                () -> {}
        );
    }

    private UIElement aspectRatioTest() {
        return container(id("aspect"),
                ElementDeclaration.builder()
                        .backgroundColor(new Color(255, 0, 0, 100))
                        .layout(LayoutConfig.builder()
                                .aspectRatio(2.0f)
                                .sizing(new Sizing(SizingAxis.fit(), SizingAxis.fit())))
                        .build(),
                () -> {
                    text("Aspect Ratio 2:1", TextElementConfig.builder()
                            .fontSize(24)
                            .textColor(new Color(255, 255, 255, 255))
                            .fontIdentifier(GameFonts.LEXEND_FONT).build());
                }
        );
    }

    private void reverseLayoutTest() {
        // RTL Test
        container(ElementDeclaration.builder()
                .backgroundColor(new Color(200, 200, 255, 255))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fixed(300), SizingAxis.fit()))
                        .layoutDirection(UI.LayoutDirection.RIGHT_TO_LEFT)
                        .childAlignment(new ChildAlignment(UI.HorizontalAlignment.RIGHT, UI.VerticalAlignment.TOP))
                        .childGap(10)
                        .padding(new Padding(10)))
                .build(), () -> {
            text("First (Right)", TextElementConfig.builder().fontSize(16).textColor(new Color(0,0,0,255)).fontIdentifier(GameFonts.LEXEND_FONT).build());
            text("Second (Left)", TextElementConfig.builder().fontSize(16).textColor(new Color(0,0,0,255)).fontIdentifier(GameFonts.LEXEND_FONT).build());
        });

        // BTT Test
        container(ElementDeclaration.builder()
                .backgroundColor(new Color(255, 200, 200, 255))
                .layout(LayoutConfig.builder()
                        .sizing(new Sizing(SizingAxis.fit(), SizingAxis.fixed(200)))
                        .layoutDirection(UI.LayoutDirection.BOTTOM_TO_TOP)
                        .childAlignment(new ChildAlignment(UI.HorizontalAlignment.CENTER, UI.VerticalAlignment.BOTTOM))
                        .childGap(10)
                        .padding(new Padding(10)))
                .build(), () -> {
            text("First (Bottom)", TextElementConfig.builder().fontSize(16).textColor(new Color(0,0,0,255)).fontIdentifier(GameFonts.LEXEND_FONT).build());
            text("Second (Top)", TextElementConfig.builder().fontSize(16).textColor(new Color(0,0,0,255)).fontIdentifier(GameFonts.LEXEND_FONT).build());
        });
    }
}
