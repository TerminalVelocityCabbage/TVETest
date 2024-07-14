package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameTextures {

    public static Identifier SMILE;
    public static Identifier TEST_PIG;

    public static void init(GameClient client) {
        //Register Textures to the VFS
        SMILE = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.TEXTURE, "smile.png").getIdentifier();
        TEST_PIG = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.TEXTURE, "test_blockbench.png").getIdentifier();
    }

}
