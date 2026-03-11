package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

import static com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory.DEFAULT_CONFIG;

public class GameConfigs {

    public static final Identifier TEST_TOML_CONFIG = DEFAULT_CONFIG.identifierOf(GameClient.ID, "test");

}
