package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameTextures {

    public static Identifier SMILE;
    public static Identifier SAD;
    public static Identifier SMILE_SMALL;
    public static Identifier SMILE_TINY;
    public static Identifier SAD_SMALL;
    public static Identifier HAPPY;

    public static void init(ResourceRegistrationEvent event) {
        //Register Textures to the VFS
        SMILE = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "smile.png").getIdentifier();
        SAD = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "sad.png").getIdentifier();
        SMILE_SMALL = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "smile_small.png").getIdentifier();
        SAD_SMALL = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "sad_small.png").getIdentifier();
        SMILE_TINY = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "smile_tiny.png").getIdentifier();
        HAPPY = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "happy.png").getIdentifier();
    }

}
