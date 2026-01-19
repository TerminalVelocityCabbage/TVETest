package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.ecs.RotateEntitiesSystem;
import com.terminalvelocitycabbage.game.common.ecs.components.PitchYawRotationComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PlayerCameraComponent;
import com.terminalvelocitycabbage.game.common.ecs.components.PositionComponent;
import com.terminalvelocitycabbage.templates.ecs.components.ModelComponent;
import com.terminalvelocitycabbage.templates.ecs.components.TransformationComponent;
import com.terminalvelocitycabbage.templates.events.EntityComponentRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.EntitySystemRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.EntityTemplateRegistrationEvent;

public class GameEntities {

    public static Identifier PLAYER_ENTITY;
    public static Identifier SMILE_SQUARE_ENTITY;
    public static Identifier SAD_SQUARE_ENTITY;
    public static Identifier MAIN_CHAR_ENTITY;

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

        var client = ClientBase.getInstance();

        PLAYER_ENTITY = event.createEntityTemplate(client.identifierOf("player"), entity -> {
            entity.addComponent(TransformationComponent.class);
            entity.addComponent(PositionComponent.class);
            entity.addComponent(PitchYawRotationComponent.class);
            entity.addComponent(PlayerCameraComponent.class);
        });

        SMILE_SQUARE_ENTITY = event.createEntityTemplate(client.identifierOf("smile_square"), entity -> {
            entity.addComponent(ModelComponent.class).setModel(GameModels.SMILE_SQUARE_MODEL);
            entity.addComponent(TransformationComponent.class).setPosition(-1.25f, 0, -2);
        });

        SAD_SQUARE_ENTITY = event.createEntityTemplate(client.identifierOf("sad_square"), entity -> {
            entity.addComponent(ModelComponent.class).setModel(GameModels.SAD_SQUARE_MODEL);
            entity.addComponent(TransformationComponent.class).setPosition(1.25f, 0, -2);
        });

        MAIN_CHAR_ENTITY = event.createEntityTemplate(client.identifierOf("main_char"), entity -> {
            entity.addComponent(ModelComponent.class).setModel(GameModels.MAIN_CHAR_MODEL);
            entity.addComponent(TransformationComponent.class).setPosition(0, 0, -45);
        });
    }
}
