package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.game.client.ecs.RotateEntitiesSystem;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.MaterialComponent;
import com.terminalvelocitycabbage.templates.ecs.components.MeshComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;
import com.terminalvelocitycabbage.templates.events.EntityComponentRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.EntitySystemRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.EntityTemplateRegistrationEvent;

public class GameEntities {

    public static void registerComponents(EntityComponentRegistrationEvent event) {
        event.registerComponent(MeshComponent.class);
        event.registerComponent(MaterialComponent.class);
        event.registerComponent(TransformationComponent.class);
        event.registerComponent(PositionComponent.class);
        event.registerComponent(PitchYawRotationComponent.class);
        event.registerComponent(PlayerCameraComponent.class);
    }

    public static void createSystems(EntitySystemRegistrationEvent event) {
        event.createSystem(RotateEntitiesSystem.class);
    }

    public static void createEntityTemplates(EntityTemplateRegistrationEvent event) {

    }
}
