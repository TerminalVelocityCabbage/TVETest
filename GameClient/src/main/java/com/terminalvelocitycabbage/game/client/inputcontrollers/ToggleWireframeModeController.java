package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.input.control.Control;
import com.terminalvelocitycabbage.engine.client.input.controller.BooleanController;
import com.terminalvelocitycabbage.engine.client.input.types.ButtonAction;
import com.terminalvelocitycabbage.game.client.registry.GameStates;

/**
 * Closes the focused window when this ToggleController is true
 */
public class ToggleWireframeModeController extends BooleanController {

    public ToggleWireframeModeController(Control... controls) {
        super(ButtonAction.RELEASED, false, controls);
    }

    @Override
    public void act() {
        if (isEnabled()) {
            var client = ClientBase.getInstance();
            client.getStateHandler().getState(GameStates.WIREFRAME_MODE).toggle();
        }
    }
}
