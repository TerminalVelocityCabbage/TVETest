package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.game.client.ecs.RotateEntitiesSystem;
import com.terminalvelocitycabbage.templates.ecs.components.*;
import com.terminalvelocitycabbage.templates.events.EntityComponentRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.EntitySystemRegistrationEvent;

public class GameEntities {

    public static void registerComponents(EntityComponentRegistrationEvent event) {
        event.registerComponent(MeshComponent.class);
        event.registerComponent(MaterialComponent.class);
        event.registerComponent(TransformationComponent.class);
        event.registerComponent(ModelComponent.class);
        event.registerComponent(ModelAnimationControllerComponent.class);
    }

    public static void createSystems(EntitySystemRegistrationEvent event) {
        event.createSystem(RotateEntitiesSystem.class);
    }
}
