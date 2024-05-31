package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.input.control.Control;
import com.terminalvelocitycabbage.engine.client.input.controller.BooleanController;
import com.terminalvelocitycabbage.engine.client.input.types.ButtonAction;

/**
 * Closes the focused window when this ToggleController is true
 */
public class CloseWindowController extends BooleanController {

    public CloseWindowController(Control... controls) {
        super(ButtonAction.PRESSED, false, controls);
    }

    @Override
    public void act() {
        if (isEnabled()) ClientBase.getInstance().getWindowManager().closeFocusedWindow();
    }
}
