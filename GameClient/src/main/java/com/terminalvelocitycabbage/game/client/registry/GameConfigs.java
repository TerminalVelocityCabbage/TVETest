package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameConfigs {

    public static Identifier TEST_TOML_CONFIG;

    public static void init(GameClient client) {
        TEST_TOML_CONFIG = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.DEFAULT_CONFIG, "test.toml");
    }
}
