package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameAnimations  {

    public static Identifier TEST_PIG;

    public static void init(GameClient client) {
        TEST_PIG = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.ANIMATION, "test_blockbench-default.animation.json").getIdentifier();
    }

}
