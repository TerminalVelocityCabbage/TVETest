package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.input.control.Control;
import com.terminalvelocitycabbage.engine.client.input.controller.BooleanController;
import com.terminalvelocitycabbage.engine.client.input.types.ButtonAction;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.graph.RenderNode;
import com.terminalvelocitycabbage.game.client.registry.GameRenderers;

/**
 * Closes the focused window when this ToggleController is true
 */
public class RecompileShadersController extends BooleanController {

    public RecompileShadersController(Control... controls) {
        super(ButtonAction.RELEASED, false, controls);
    }

    @Override
    public void act() {
        if (isEnabled()) {
            var client = ClientBase.getInstance();
            var windowManager = client.getWindowManager();
            var activeRenderGraph = windowManager.getPropertiesFromWindow(windowManager.getFocusedWindow()).getActiveScene().getRenderGraph();
            ((RenderNode) client.getRenderGraphRegistry().get(activeRenderGraph).getNode(GameRenderers.DRAW_SCENE_RENDER_NODE)).recompileShaders();
            Log.info("reloaded shaders...");
        }
    }
}
