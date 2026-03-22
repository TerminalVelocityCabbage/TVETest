package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.controller.GroupedController4f;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;

public class LookAroundController extends GroupedController4f {

    private final ControlGroup modifier;

    public LookAroundController(ControlGroup modifier, ControlGroup forward, ControlGroup backward, ControlGroup left, ControlGroup right) {
        super(forward, backward, left, right);
        this.modifier = modifier;
    }

    @Override
    public void act() {
        if (modifier.isPressed()) {
            getPlayer().getComponent(PitchYawRotationComponent.class).rotate(getRightAmount() - getLeftAmount(), getUpwardAmount() - getDownwardAmount());
        }
    }

    private Entity getPlayer() {
        return GameClient.getInstance().getManager().getFirstEntityWith(PlayerCameraComponent.class, PositionComponent.class, PitchYawRotationComponent.class);

    }
}
