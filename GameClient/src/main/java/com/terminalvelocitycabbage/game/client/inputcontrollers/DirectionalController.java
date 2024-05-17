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
        Log.info(getForwardAmount() + " " + getBackwardAmount() + " " + getLeftAmount() + " " + getRightAmount() + " " + getUpAmount() + " " + getDownAmount());
    }
}
