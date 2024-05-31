package com.terminalvelocitycabbage.game.common.ecs;

import com.terminalvelocitycabbage.engine.ecs.Component;
import org.joml.Vector3f;

public class PositionComponent implements Component {

    Vector3f position = new Vector3f();

    @Override
    public void setDefaults() {
        position.set(0, 0, 0);
    }

    @Override
    public void cleanup() {
        Component.super.cleanup();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position = position.set(x, y, z);
    }
}
