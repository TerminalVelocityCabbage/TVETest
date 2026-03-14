package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.input.control.Control;
import com.terminalvelocitycabbage.engine.client.input.controller.BooleanController;
import com.terminalvelocitycabbage.engine.client.input.types.ButtonAction;
import com.terminalvelocitycabbage.game.client.registry.GameStates;

public class ToggleDebugRendererController extends BooleanController {

    public ToggleDebugRendererController(Control... controls) {
        super(ButtonAction.RELEASED, false, controls);
    }

    @Override
    public void act() {
        if (isEnabled()) {
            var client = ClientBase.getInstance();
            var state = client.getStateHandler().<Boolean>getState(GameStates.DEBUG_RENDERER);
            client.getStateHandler().updateState(GameStates.DEBUG_RENDERER, !state.getValue());
        }
    }
}
