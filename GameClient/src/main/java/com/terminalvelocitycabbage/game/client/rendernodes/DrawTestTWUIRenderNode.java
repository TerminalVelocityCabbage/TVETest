package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.ui.UIElement;
import com.terminalvelocitycabbage.engine.client.ui.UIRenderNode;
import com.terminalvelocitycabbage.engine.client.ui.data.ElementDeclaration;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.registry.GameFonts;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;
import com.terminalvelocitycabbage.templates.events.UIClickEvent;
import com.terminalvelocitycabbage.templates.events.UIScrollEvent;

public class DrawTestTWUIRenderNode extends UIRenderNode {

    public DrawTestTWUIRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    @Override
    protected Identifier[] getInterestedEvents() {
        return new Identifier[] { UIClickEvent.EVENT, UIScrollEvent.EVENT };
    }

    @Override
    protected void declareUI() {
        container("bg-[1,1,1,0.392] grow pt-[10] pl-[15]", () -> {
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
        container(id("imageContainer"), "bg-[1,1,1,0.196] fit layout-x-[ltr] gap-[10] p-[10]", () -> {
            // Natural size image
            image(id("naturalSize"), ElementDeclaration.of("border-color-[1,1,1,1] border-width-[2] img-[" + GameTextures.HAPPY + "] atlas-[" + GameTextures.UI_TEXTURE_ATLAS + "]"));

            // Fit height, fixed width
            image(id("fixedWidthFitHeight"), ElementDeclaration.of("border-color-[1,0,0,1] border-width-[2] aspect-[1] w-[100px] fit-y img-[" + GameTextures.HAPPY + "] atlas-[" + GameTextures.UI_TEXTURE_ATLAS + "]"));

            // Fit width, fixed height
            image(id("fitWidthFixedHeight"), ElementDeclaration.of("border-color-[0,1,0,1] border-width-[2] fit-x h-[100px] aspect-[1] img-[" + GameTextures.HAPPY + "] atlas-[" + GameTextures.UI_TEXTURE_ATLAS + "]"));

            // Fixed both (squished)
            image(id("fixedBoth"), ElementDeclaration.of("border-color-[0,0,1,1] border-width-[2] w-[150px] h-[50px] img-[" + GameTextures.HAPPY + "] atlas-[" + GameTextures.UI_TEXTURE_ATLAS + "]"));
        });
    }

    private void marginTest() {
        container(id("marginContainer"), "bg-[0.392,0.392,1,1] fit layout-x-[ltr]", () -> {
            // Child with margin
            container(id("marginChild1"), "bg-[1,0.392,0.392,1] w-[50px] h-[50px] ml-[20] mt-[10] mb-[5] mr-[10]", () -> {});

            // Child without margin
            container(id("marginChild2"), "bg-[0.392,1,0.392,1] w-[50px] h-[50px]", () -> {});
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
        container(backId, "bg-[" + (isHovered(backId) ? "1,0.392,0.392,1" : "0.784,0,0,1") + "] float-root z-[10] float-offset-x-[50] float-offset-y-[450] w-[200px] h-[200px] align-x-[center] align-y-[center]", () -> {
            text("z-index: 10\nClicks: " + backClicks.getValue(), "text-size-[20] text-color-[1,1,1,1] font-[" + GameFonts.LEXEND_FONT + "]");
        });

        // Foreground floating element (higher zIndex, partially overlapping)
        container(frontId, "bg-[" + (isHovered(frontId) ? "0.392,1,0.392,1" : "0,0.784,0,1") + "] float-root z-[20] float-offset-x-[150] float-offset-y-[550] pointer-capture w-[200px] h-[200px] align-x-[center] align-y-[center]", () -> {
            text("z-index: 20\nCapture\nClicks: " + frontClicks.getValue(), "text-size-[20] text-color-[1,1,1,1] font-[" + GameFonts.LEXEND_FONT + "]");
        });

        // Passthrough floating element (even higher zIndex, but passthrough)
        container(passId, "bg-[" + (isHovered(passId) ? "0.392,0.392,1,0.588" : "0,0,0.784,0.392") + "] float-root z-[30] float-offset-x-[250] float-offset-y-[450] pointer-passthrough w-[150px] h-[150px] align-x-[center] align-y-[center]", () -> {
            text("z-index: 30\nPassthrough\nClicks: " + passClicks.getValue(), "text-size-[20] text-color-[1,1,1,1] font-[" + GameFonts.LEXEND_FONT + "]");
        });
    }

    private void scrollTest() {
        scrollableContainer(id("myScrollable"),
                ElementDeclaration.of("bg-[0.196,0.196,0.196,1] layout-y-[ttb] w-[200px] grow-y p-[10] gap-[5]"),
                ElementDeclaration.of("bg-[1,0,0,1] w-[10px] fit-y"),
                () -> {
                    for (int i = 0; i < 20; i++) {
                        int index = i;
                        container("bg-[0.392,0.392,1,1] w-[50px] h-[30px] align-x-[center] align-y-[center]", () -> {
                            text("Item " + index, "text-color-[1,1,1,1] font-[" + GameFonts.LEXEND_FONT + "]");
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

        container(buttonId, "bg-[" + (isHovered(buttonId) ? "0.588,0.588,0.588,1" : "0.392,0.392,0.392,1") + "] rounded-[10] w-[200px] h-[50px] align-x-[center] align-y-[center]", () -> {
            text("Clicked " + clickCount.getValue() + " times", "text-size-[20] text-color-[1,1,1,1] font-[" + GameFonts.LEXEND_FONT + "]");
        });
    }

    private void borderAndCornersTest() {
        container("bg-[0,0,0,0.196] roundedtl-[" + (float) (20f * Math.sin(ClientBase.getInstance().getRuntime() / 1000f) + 20f) + "] roundedtr-[0] roundedbl-[20] roundedbr-[0] border-color-[1,1,1,1] border-width-[" + (int) (5 * Math.sin(ClientBase.getInstance().getRuntime() / 1500f) + 5) + "] w-[200px] h-[100px] p-[10]", () -> {
            text("Border & Corners", "text-size-[20] text-color-[1,1,1,1] font-[" + GameFonts.LEXEND_FONT + "]");
        });
    }

    private void textWrapTest() {
        container("bg-[1,1,0,0.196] w-[30%] fit-y p-[10]", () -> {
            text("This is a long piece of text that should wrap because it is inside a container with a fixed width of 200 pixels. We are testing if the layout engine correctly calculates the height of the text after wrapping.",
                    "text-size-[16] text-color-[0,0,0,1] font-[" + GameFonts.LEXEND_FONT + "] text-wrap-words text-left line-height-[10]");
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
        return container("bg-[0,0,1,0.392] float-element-[" + id("aspect") + "] attach-[left] to-[left] w-[100px] h-[100px]", () -> {});
    }

    private UIElement aspectRatioTest() {
        return container(id("aspect"), "bg-[1,0,0,0.392] aspect-[2.0] fit", () -> {
            text("Aspect Ratio 2:1", "text-size-[24] text-color-[1,1,1,1] font-[" + GameFonts.LEXEND_FONT + "]");
        });
    }

    private void reverseLayoutTest() {
        // RTL Test
        container("bg-[0.784,0.784,1,1] w-[300px] fit-y layout-x-[rtl] align-x-[right] align-y-[top] gap-[10] p-[10]", () -> {
            text("First (Right)", "text-size-[16] text-color-[0,0,0,1] font-[" + GameFonts.LEXEND_FONT + "]");
            text("Second (Left)", "text-size-[16] text-color-[0,0,0,1] font-[" + GameFonts.LEXEND_FONT + "]");
        });

        // BTT Test
        container("bg-[1,0.784,0.784,1] fit-x h-[200px] layout-y-[btt] align-x-[center] align-y-[bottom] gap-[10] p-[10]", () -> {
            text("First (Bottom)", "text-size-[16] text-color-[0,0,0,1] font-[" + GameFonts.LEXEND_FONT + "]");
            text("Second (Top)", "text-size-[16] text-color-[0,0,0,1] font-[" + GameFonts.LEXEND_FONT + "]");
        });
    }
}
