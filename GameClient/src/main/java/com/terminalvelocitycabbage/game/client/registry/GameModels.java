package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.model.Model;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.ModelConfigRegistrationEvent;

public class GameModels {

    public static Identifier SMILE_SQUARE_MODEL;
    public static Identifier SAD_SQUARE_MODEL;
    public static Identifier MAIN_CHAR_MODEL;

    public static void init(ModelConfigRegistrationEvent event) {
        SMILE_SQUARE_MODEL = registerModel(event, "smile_square", GameMeshes.SQUARE_MESH, GameTextures.SMILE);
        SAD_SQUARE_MODEL = registerModel(event, "sad_square", GameMeshes.SQUARE_MESH, GameTextures.SAD);
        MAIN_CHAR_MODEL = registerModel(event, "main_char", GameMeshes.MAIN_CHAR_MESH, GameTextures.MAIN_CHAR);
    }

    private static Identifier registerModel(ModelConfigRegistrationEvent event, String modelName, Identifier mesh, Identifier texture) {
        return event.register(ClientBase.getInstance().identifierOf(modelName), new Model(mesh, texture)).getIdentifier();
    }

}
