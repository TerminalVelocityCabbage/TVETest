package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.ecs.RotateEntitiesSystem;
import com.terminalvelocitycabbage.game.common.GameCommon;
import com.terminalvelocitycabbage.templates.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;
import com.terminalvelocitycabbage.templates.events.EntityComponentRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.EntitySystemRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.EntityTemplateRegistrationEvent;

public class GameEntities {

    public static Identifier PLAYER_ENTITY;
    public static Identifier SMILE_SQUARE_ENTITY;
    public static Identifier SAD_SQUARE_ENTITY;

    public static void registerComponents(EntityComponentRegistrationEvent event) {
        event.registerComponent(ModelComponent.class);
        event.registerComponent(TransformationComponent.class);
        event.registerComponent(PositionComponent.class);
        event.registerComponent(PitchYawRotationComponent.class);
        event.registerComponent(PlayerCameraComponent.class);
    }

    public static void createSystems(EntitySystemRegistrationEvent event) {
        event.createSystem(RotateEntitiesSystem.class);
    }

    public static void createEntityTemplates(EntityTemplateRegistrationEvent event) {
        PLAYER_ENTITY = event.createEntityTemplateFromFile(GameCommon.ID, "player");
        SMILE_SQUARE_ENTITY = event.createEntityTemplateFromFile(GameCommon.ID, "smile_square");
        SAD_SQUARE_ENTITY = event.createEntityTemplateFromFile(GameCommon.ID, "sad_square");
    }
}
