package com.terminalvelocitycabbage.game.client.inputcontrollers;

import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.controller.GroupedController6f;
import com.terminalvelocitycabbage.engine.ecs.ComponentFilter;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import org.joml.Vector3f;

public class MoveController extends GroupedController6f {

    public MoveController(ControlGroup forward, ControlGroup backward, ControlGroup left, ControlGroup right, ControlGroup up, ControlGroup down) {
        super(forward, backward, left, right, up, down);
    }

    @Override
    public void act() {
        var player = getPlayer();
        var rotation = player.getComponent(PitchYawRotationComponent.class).getRotation();
        Vector3f movement = new Vector3f((
                getLeftAmount() - getRightAmount()) * .25f,
                (getDownAmount() - getUpAmount()) * .25f,
                (getForwardAmount() - getBackwardAmount()) * .25f
        );
        movement.rotateY(rotation.x(), movement);
        getPlayer().getComponent(PositionComponent.class).move(movement);
    }

    private Entity getPlayer() {
        return GameClient.getInstance().getManager().getFirstMatchingEntity(ComponentFilter.builder()
                .allOf(PlayerCameraComponent.class, PositionComponent.class, PitchYawRotationComponent.class)
                .build());
    }
}
