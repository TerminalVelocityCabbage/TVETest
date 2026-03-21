package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.model.MeshTexturePair;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.data.MeshData;
import com.terminalvelocitycabbage.templates.events.CreateModelsFromTVModelsEvent;
import com.terminalvelocitycabbage.templates.events.ModelConfigRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.TVModelRegistrationEvent;

import java.util.List;

public class GameModels {

    public static Identifier SMILE_SQUARE_MODEL;
    public static Identifier SAD_SQUARE_MODEL;
    public static Identifier PIG_MODEL;
    public static Identifier TYRANNOSAURUS_MODEL;

    public static void init(ModelConfigRegistrationEvent event) {
        SMILE_SQUARE_MODEL = event.registerModel(GameClient.ID, "smile_square", List.of(new MeshTexturePair(GameMeshes.SQUARE_MESH, GameTextures.SMILE)));
        SAD_SQUARE_MODEL = event.registerModel(GameClient.ID, "sad_square", List.of(new MeshTexturePair(GameMeshes.SQUARE_MESH, GameTextures.SAD)));
        PIG_MODEL = new Identifier(GameClient.ID, "model_variant", "pig_test_default");
        TYRANNOSAURUS_MODEL = new Identifier(GameClient.ID, "model_variant", "tyrannosaurus_adult_v2_male");
    }

    public static void registerTVModels(TVModelRegistrationEvent event) {
        event.registerTVModel(GameClient.ID, ResourceCategory.MODEL.identifierOf(GameClient.ID, "pig_test"));
        event.registerTVModel(GameClient.ID, ResourceCategory.MODEL.identifierOf(GameClient.ID, "tyrannosaurus_adult_v2"));
    }

    public static void createModelsFromTVModels(CreateModelsFromTVModelsEvent event) {
        event.createAllModels(GameClient.ID, MeshData.MESH_FORMAT);
    }
}
