package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameAnimations  {

    public static Identifier TEST_PIG;

    public static void init(ResourceRegistrationEvent event) {
        TEST_PIG = event.registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceCategory.ANIMATION, "test_blockbench-default.animation.json").getIdentifier();
    }

}
