package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.controller.GroupedController6f;
import com.terminalvelocitycabbage.engine.debug.Log;

public class DirectionalController extends GroupedController6f {

    public DirectionalController(ControlGroup forward, ControlGroup backward, ControlGroup left, ControlGroup right, ControlGroup up, ControlGroup down) {
        super(forward, backward, left, right, up, down);
    }

    @Override
    public void act() {
        if (getForwardAmount() < 0.1 && getBackwardAmount() < 0.1 && getLeftAmount() < 0.1 && getRightAmount() < 0.1 && getUpAmount() < 0.1 && getDownAmount() < 0.1) return;
        Log.info(getForwardAmount() + " " + getBackwardAmount() + " " + getLeftAmount() + " " + getRightAmount() + " " + getUpAmount() + " " + getDownAmount());
    }
}
