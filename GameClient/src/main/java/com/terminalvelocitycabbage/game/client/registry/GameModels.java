package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ModelConfigRegistrationEvent;

public class GameModels {

    public static Identifier SMILE_SQUARE_MODEL;
    public static Identifier SAD_SQUARE_MODEL;

    public static void init(ModelConfigRegistrationEvent event) {
        SMILE_SQUARE_MODEL = event.registerModel(GameClient.ID, "smile_square", GameMeshes.SQUARE_MESH, GameTextures.SMILE);
        SAD_SQUARE_MODEL = event.registerModel(GameClient.ID, "sad_square", GameMeshes.SQUARE_MESH, GameTextures.SAD);
    }
}
