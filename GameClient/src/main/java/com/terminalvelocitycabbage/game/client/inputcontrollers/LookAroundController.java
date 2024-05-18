package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.controller.GroupedController4f;
import com.terminalvelocitycabbage.engine.debug.Log;

public class LookAroundController extends GroupedController4f {

    public LookAroundController(ControlGroup forward, ControlGroup backward, ControlGroup left, ControlGroup right) {
        super(forward, backward, left, right);
    }

    @Override
    public void act() {
        if (getForwardAmount() < 0.002 && getBackwardAmount() < 0.002 && getLeftAmount() < 0.002 && getRightAmount() < 0.002) return;
        Log.info(getUpwardAmount() + " " + getDownwardAmount() + " " + getLeftAmount() + " " + getRightAmount());
    }
}
