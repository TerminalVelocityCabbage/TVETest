package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameAnimations  {

    static Identifier TEST_PIG_WALK;

    public static void init(GameClient client) {
        TEST_PIG_WALK = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.ANIMATION, "test_blockbench.animation.json").getIdentifier();
    }

}
