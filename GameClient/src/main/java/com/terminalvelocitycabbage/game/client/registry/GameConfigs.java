package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameConfigs {

    public static Identifier TEST_TOML_CONFIG;

    public static void init(ResourceRegistrationEvent event) {
        TEST_TOML_CONFIG = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.DEFAULT_CONFIG, "test.toml").getIdentifier();
    }
}
