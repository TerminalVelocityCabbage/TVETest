package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.controller.GroupedController4f;
import com.terminalvelocitycabbage.engine.debug.Log;

public class DirectionalController extends GroupedController4f {

    public DirectionalController(ControlGroup forward, ControlGroup backward, ControlGroup left, ControlGroup right) {
        super(forward, backward, left, right);
    }

    @Override
    public void act() {
        Log.info(getForwardAmount() + " " + getBackwardAmount() + " " + getLeftAmount() + " " + getRightAmount());
    }
}
