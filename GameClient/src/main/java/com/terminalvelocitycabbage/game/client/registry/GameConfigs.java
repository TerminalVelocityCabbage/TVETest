package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameConfigs {

    public static Identifier TEST_TOML_CONFIG;

    public static void init(GameClient client, Identifier sourceIdentifier) {
        TEST_TOML_CONFIG = client.getFileSystem().registerResource(sourceIdentifier, ResourceType.DEFAULT_CONFIG, "test.toml");
    }
}
