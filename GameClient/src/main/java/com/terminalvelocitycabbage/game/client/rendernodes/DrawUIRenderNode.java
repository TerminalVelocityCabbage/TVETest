package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.client.ui.ContainerLayout;
import com.terminalvelocitycabbage.engine.client.ui.Layout;
import com.terminalvelocitycabbage.engine.client.ui.Style;
import com.terminalvelocitycabbage.engine.graph.UIRenderNode;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.registry.GameFonts;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;

import static com.terminalvelocitycabbage.engine.client.ui.ContainerLayout.Anchor.*;
import static com.terminalvelocitycabbage.engine.client.ui.ContainerLayout.JustifyChildren.BOTTOM_RIGHT;
import static com.terminalvelocitycabbage.engine.client.ui.ContainerLayout.PlacementDirection.*;
import static com.terminalvelocitycabbage.engine.client.ui.Layout.Unit.PERCENT;
import static com.terminalvelocitycabbage.engine.client.ui.Layout.Unit.PIXELS;

public class DrawUIRenderNode extends UIRenderNode {

    public DrawUIRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    private static Identifier LEFT_CONTAINER;
    private static Identifier TOP_CONTAINER;
    private static Identifier BOTTOM_CONTAINER;
    private static Identifier HAPPY_BOX_ELEMENT;
    private static Identifier BASIC_TEXT;

    @Override
    public void registerUIElements(ElementRegistry elementRegistry) {

        var client = ClientBase.getInstance();

        HAPPY_BOX_ELEMENT = elementRegistry.registerElement(
                client.identifierOf("happy_box"),
                new Layout(100, 100),
                Style.builder().setTexture(GameTextures.HAPPY).build());
        LEFT_CONTAINER = elementRegistry.registerElement(
                client.identifierOf("left_container"),
                new ContainerLayout(new Layout.Dimension(400, PIXELS), new Layout.Dimension(100, PERCENT), CENTER_RIGHT, LEFT, ContainerLayout.JustifyChildren.LEFT),
                Style.builder().setTexture(GameTextures.SMILE).build());
        TOP_CONTAINER = elementRegistry.registerElement(
                client.identifierOf("top_container"),
                new ContainerLayout(new Layout.Dimension(100, PERCENT), new Layout.Dimension(50, PERCENT), TOP_CENTER, DOWN, ContainerLayout.JustifyChildren.LEFT),
                Style.builder().setTexture(GameTextures.SMILE).build());
        BOTTOM_CONTAINER = elementRegistry.registerElement(
                client.identifierOf("bottom_container"),
                new ContainerLayout(new Layout.Dimension(100, PERCENT), new Layout.Dimension(50, PERCENT), BOTTOM_CENTER, UP, BOTTOM_RIGHT),
                Style.builder().setTexture(GameTextures.SMILE).build());
        BASIC_TEXT = elementRegistry.registerElement(
                client.identifierOf("basic_text"),
                new Layout(100, 100),
                Style.builder().setFont(GameFonts.GETHO_FONT_32).build());
    }

    @Override
    public void drawUIElements(Scene scene) {

        drawText(BASIC_TEXT, "Hello World!");

        if (startContainer(LEFT_CONTAINER)) {
            if (startContainer(TOP_CONTAINER)) {
                drawBox(HAPPY_BOX_ELEMENT);
                drawBox(HAPPY_BOX_ELEMENT);
                drawBox(HAPPY_BOX_ELEMENT);
            }
            endContainer();
            if (startContainer(BOTTOM_CONTAINER)) {
                drawBox(HAPPY_BOX_ELEMENT);
                drawBox(HAPPY_BOX_ELEMENT);
                drawBox(HAPPY_BOX_ELEMENT);
            }
            endContainer();
        }
        endContainer();

    }
}
