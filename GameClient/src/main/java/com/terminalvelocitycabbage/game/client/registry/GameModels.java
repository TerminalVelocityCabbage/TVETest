package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameModels {

    static Identifier TEST_PIG;

    public static void init(GameClient client) {
        TEST_PIG = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.MODEL, "test_blockbench.geo.json").getIdentifier();
    }

}
