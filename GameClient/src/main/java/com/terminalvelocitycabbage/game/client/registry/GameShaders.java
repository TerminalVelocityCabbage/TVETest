package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameShaders {

    public static Identifier TEST_VERTEX_SHADER;
    public static Identifier TEST_FRAGMENT_SHADER;

    public static void init(GameClient client) {
        TEST_VERTEX_SHADER = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.SHADER, "default.vert").getIdentifier();
        TEST_FRAGMENT_SHADER = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.SHADER, "default.frag").getIdentifier();
    }

}
