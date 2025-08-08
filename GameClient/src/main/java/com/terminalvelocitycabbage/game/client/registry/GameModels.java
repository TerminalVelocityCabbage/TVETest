package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.model.Model;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.ModelConfigRegistrationEvent;

public class GameModels {

    public static Identifier SMILE_SQUARE_MODEL;

    public static void init(ModelConfigRegistrationEvent event) {
        SMILE_SQUARE_MODEL = registerModel(event, "smile_square", GameMeshes.SQUARE_MESH, GameTextures.SMILE);
    }

    private static Identifier registerModel(ModelConfigRegistrationEvent event, String modelName, Identifier mesh, Identifier texture) {
        return event.register(ClientBase.getInstance().identifierOf(modelName), new Model(mesh, texture)).getIdentifier();
    }

}
