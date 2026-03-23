package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.model.MeshTexturePair;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.data.MeshData;
import com.terminalvelocitycabbage.templates.events.CreateModelsFromTVModelsEvent;
import com.terminalvelocitycabbage.templates.events.ModelConfigRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.TVAnimationRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.TVModelRegistrationEvent;

import java.util.List;

import static com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory.ANIMATION;
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
        event.registerTVAnimation(ID, ANIMATION.identifierOf(ID, "walk"));
    }

    public static void createModelsFromTVModels(CreateModelsFromTVModelsEvent event) {
        event.createAllModels(ID, MeshData.ANIMATED_MESH_FORMAT);
    }
}
