package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.ecs.RotateEntitiesSystem;
import com.terminalvelocitycabbage.templates.ecs.components.MaterialComponent;
import com.terminalvelocitycabbage.templates.ecs.components.MeshComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;

public class GameEntities {

    public static void init(GameClient client) {
        client.getManager().registerComponent(MeshComponent.class);
        client.getManager().registerComponent(ModelComponent.class);
        client.getManager().registerComponent(MaterialComponent.class);
        client.getManager().registerComponent(TransformationComponent.class);

        client.getManager().createSystem(RotateEntitiesSystem.class);
    }

}
