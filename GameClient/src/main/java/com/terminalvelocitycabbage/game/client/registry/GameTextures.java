package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameTextures {

    public static Identifier SMILE;
    public static Identifier TEST_PIG;
    public static Identifier QUAXLY;

    public static void init(ResourceRegistrationEvent event) {
        //Register Textures to the VFS
        SMILE = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.TEXTURE, "smile.png").getIdentifier();
        TEST_PIG = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.TEXTURE, "test_blockbench.png").getIdentifier();
        QUAXLY = client.getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.TEXTURE, "quaxly.png").getIdentifier();
    }

}
