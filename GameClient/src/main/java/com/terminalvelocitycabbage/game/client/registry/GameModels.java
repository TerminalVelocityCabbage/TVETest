package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.data.MeshData;
import com.terminalvelocitycabbage.templates.ecs.components.VelocityComponent;
import com.terminalvelocitycabbage.templates.events.AnimationConfigurationEvent;
import com.terminalvelocitycabbage.templates.events.ModelConfigRegistrationEvent;
import org.joml.Vector3f;

import static com.terminalvelocitycabbage.game.client.GameClient.ID;

public class GameModels {

    public static Identifier SMILE_SQUARE_MODEL;
    public static Identifier SAD_SQUARE_MODEL;
    public static Identifier PIG_MODEL;
    public static Identifier TYRANNOSAURUS_MODEL;

    public static void init(ModelConfigRegistrationEvent event) {

        // Standard Models
        SMILE_SQUARE_MODEL = event.registerModel(ID, "smile_square", GameMeshes.SQUARE_MESH, GameTextures.SMILE);
        SAD_SQUARE_MODEL = event.registerModel(ID, "sad_square", GameMeshes.SQUARE_MESH, GameTextures.SAD);

        // Register TV Models
        event.registerTVModel(ID, "pig_test", MeshData.ANIMATED_MESH_FORMAT);
        event.registerTVModel(ID, "tyrannosaurus_adult_v2", MeshData.ANIMATED_MESH_FORMAT);

        //Register identifiers to variants of TVModels
        PIG_MODEL = event.variantIdentifier(ID, "pig_test", "default");
        TYRANNOSAURUS_MODEL = event.variantIdentifier(ID, "tyrannosaurus_adult_v2", "default");
    }

    public static void initAnimations(AnimationConfigurationEvent event) {

        //Register allowable variable types in animation controller expressions
        event.registerVariable("velocity", Vector3f.class, entity -> entity.getComponent(VelocityComponent.class).getVelocity());

        //Register animation controllers
        event.registerTVAnimationController(ID, ResourceCategory.ANIMATION_CONTROLLER.identifierOf(ID, "tyrannosaurus_adult_v2"));
    }

}
