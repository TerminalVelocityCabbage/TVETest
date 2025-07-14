package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.controller.GroupedController4f;
import com.terminalvelocitycabbage.engine.ecs.ComponentFilter;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;

import java.util.Set;

public class LookAroundController extends GroupedController4f {

    public LookAroundController(ControlGroup forward, ControlGroup backward, ControlGroup left, ControlGroup right) {
        super(forward, backward, left, right);
    }

    @Override
    public void act() {
        getPlayer().getComponent(PitchYawRotationComponent.class).rotate(getRightAmount() - getLeftAmount(), getUpwardAmount() - getDownwardAmount());
    }

    private Entity getPlayer() {
        return GameClient.getInstance().getManager().getFirstEntityWith(PlayerCameraComponent.class, PositionComponent.class, PitchYawRotationComponent.class);

    }
}
