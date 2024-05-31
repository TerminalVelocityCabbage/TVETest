package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.input.control.Control;
import com.terminalvelocitycabbage.engine.client.input.controller.FloatController;
import com.terminalvelocitycabbage.engine.debug.Log;

public class TestAxisController extends FloatController {

    public TestAxisController(Control... controls) {
        super(controls);
    }

    @Override
    public void act() {
        if (getAmount() > .1f) Log.info(getAmount());
    }
}
