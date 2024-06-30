package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameShaders {

    public static void init(GameClient client, Identifier sourceIdentifier) {
        client.getFileSystem().registerResource(sourceIdentifier, ResourceType.SHADER, "default.vert");
        client.getFileSystem().registerResource(sourceIdentifier, ResourceType.SHADER, "default.frag");
    }

}
