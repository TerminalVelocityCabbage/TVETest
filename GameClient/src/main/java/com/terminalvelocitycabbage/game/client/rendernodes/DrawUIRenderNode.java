package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.client.ui.Layout;
import com.terminalvelocitycabbage.engine.client.ui.Style;
import com.terminalvelocitycabbage.engine.graph.UIRenderNode;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;

public class DrawUIRenderNode extends UIRenderNode {

    public DrawUIRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    private static Identifier HAPPY_BOX_ELEMENT;

    @Override
    public void registerUIElements(ElementRegistry elementRegistry) {

        var client = ClientBase.getInstance();

        HAPPY_BOX_ELEMENT = elementRegistry.registerElement(client.identifierOf("happy_box"), new Layout(600, 400), Style.builder().setTexture(GameTextures.HAPPY).build());
    }

    @Override
    public void drawUIElements(Scene scene) {

        drawBox(HAPPY_BOX_ELEMENT);

    }
}
