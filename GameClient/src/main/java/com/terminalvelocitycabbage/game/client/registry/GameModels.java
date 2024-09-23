package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameModels {

    public static Identifier TEST_PIG;
    public static Identifier QUAXLY;

    public static void init(ResourceRegistrationEvent event) {
        TEST_PIG = event.registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceCategory.MODEL, "test_blockbench.geo.json").getIdentifier();
        QUAXLY = event.registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceCategory.MODEL, "quaxly.geo.json").getIdentifier();
    }

}
