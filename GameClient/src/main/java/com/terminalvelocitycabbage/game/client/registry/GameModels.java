package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.model.MeshTexturePair;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.data.MeshData;
import com.terminalvelocitycabbage.templates.ecs.components.VelocityComponent;
import com.terminalvelocitycabbage.templates.events.*;
import org.joml.Vector3f;

import java.util.List;

import static com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory.MODEL;
import static com.terminalvelocitycabbage.game.client.GameClient.ID;

public class GameModels {

    public static Identifier SMILE_SQUARE_MODEL;
    public static Identifier SAD_SQUARE_MODEL;
    public static Identifier PIG_MODEL;
    public static Identifier TYRANNOSAURUS_MODEL;

    public static void init(ModelConfigRegistrationEvent event) {
        SMILE_SQUARE_MODEL = event.registerModel(ID, "smile_square", List.of(new MeshTexturePair(GameMeshes.SQUARE_MESH, GameTextures.SMILE)));
        SAD_SQUARE_MODEL = event.registerModel(ID, "sad_square", List.of(new MeshTexturePair(GameMeshes.SQUARE_MESH, GameTextures.SAD)));
        PIG_MODEL = event.variantIdentifier(ID, "pig_test", "default");
        TYRANNOSAURUS_MODEL = event.variantIdentifier(ID, "tyrannosaurus_adult_v2", "default");
    }

    public static void registerTVModels(TVModelRegistrationEvent event) {
        event.registerTVModel(ID, MODEL.identifierOf(ID, "pig_test"));
        event.registerTVModel(ID, MODEL.identifierOf(ID, "tyrannosaurus_adult_v2"));
    }

    public static void registerTVAnimations(TVAnimationRegistrationEvent event) {
        event.registerTVAnimation(ID, "tyrannosaurus_adult_v2", "walk");
        event.registerTVAnimation(ID, "tyrannosaurus_adult_v2", "run");
        event.registerTVAnimation(ID, "tyrannosaurus_adult_v2", "turn_left");
        event.registerTVAnimation(ID, "tyrannosaurus_adult_v2", "turn_right");
        event.registerTVAnimation(ID, "tyrannosaurus_adult_v2", "scratch");
        event.registerTVAnimation(ID, "tyrannosaurus_adult_v2", "call");
    }

    public static void registerTVAnimationControllers(TVAnimationControllerRegistrationEvent event) {
        event.registerTVAnimationController(ID, ResourceCategory.ANIMATION_CONTROLLER.identifierOf(ID, "tyrannosaurus_adult_v2"));
    }

    public static void registerAnimationVariables(AnimationControllerVariableRegistrationEvent event) {
        event.registerVariable("velocity", Vector3f.class, entity -> {
            return entity.getComponent(VelocityComponent.class).getVelocity();
        });
    }

    public static void createModelsFromTVModels(CreateModelsFromTVModelsEvent event) {
        event.createAllModels(ID, MeshData.ANIMATED_MESH_FORMAT);
    }
}
