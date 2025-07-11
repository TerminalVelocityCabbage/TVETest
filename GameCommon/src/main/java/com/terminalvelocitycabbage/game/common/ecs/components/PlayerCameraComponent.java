package com.terminalvelocitycabbage.game.common.ecs.components;

import com.terminalvelocitycabbage.engine.client.renderer.Projection;
import com.terminalvelocitycabbage.engine.ecs.Component;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import org.joml.Matrix4f;

public class PlayerCameraComponent implements Component {

    private static final Projection PERSPECTIVE = new Projection(Projection.Type.PERSPECTIVE, 60, 0.1f, 1000f);
    private final Matrix4f viewMatrix = new Matrix4f();

    @Override
    public void setDefaults() {
        viewMatrix.identity();
    }

    @Override
    public void cleanup() {
        Component.super.cleanup();
    }

    public Matrix4f getProjectionMatrix() {
        return PERSPECTIVE.getProjectionMatrix();
    }

    public Matrix4f getViewMatrix(Entity entity) {

        var currentPosition = entity.getComponent(PositionComponent.class).getPosition();
        var currentRotation = entity.getComponent(PitchYawRotationComponent.class).getRotation();

        return viewMatrix.identity().rotateX(-currentRotation.y).rotateY(-currentRotation.x).translate(currentPosition);
    }

    public void updateProjectionMatrix(int width, int height) {
        PERSPECTIVE.updateProjectionMatrix(width, height);
    }
}
