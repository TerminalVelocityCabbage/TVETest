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
        SMILE = event.registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "smile.png").getIdentifier();
        TEST_PIG = event.registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "test_blockbench.png").getIdentifier();
        QUAXLY = event.registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "quaxly.png").getIdentifier();
    }

}
