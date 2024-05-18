package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.controller.GroupedController2f;
import com.terminalvelocitycabbage.engine.debug.Log;

public class ScrollController extends GroupedController2f {

    public ScrollController(ControlGroup up, ControlGroup down) {
        super(up, down);
    }

    @Override
    public void act() {
        if (getSummedAmount() == 0) return;
        Log.info(getSummedAmount());
    }
}
