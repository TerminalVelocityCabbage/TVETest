package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ConfigureTexturesEvent;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameTextures {

    //Texture Resources
    public static Identifier SMILE;
    public static Identifier SAD;
    public static Identifier SMILE_SMALL;
    public static Identifier SMILE_TINY;
    public static Identifier SAD_SMALL;
    public static Identifier HAPPY;

    //Atlases
    public static Identifier DEFAULT_SCENE_ATLAS;
    public static Identifier UI_ATLAS;

    public static void registerResources(ResourceRegistrationEvent event) {
        //Register Textures to the VFS
        SMILE = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "smile.png").getIdentifier();
        SAD = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "sad.png").getIdentifier();
        SMILE_SMALL = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "smile_small.png").getIdentifier();
        SAD_SMALL = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "sad_small.png").getIdentifier();
        SMILE_TINY = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "smile_tiny.png").getIdentifier();
        HAPPY = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.TEXTURE, "happy.png").getIdentifier();
    }

    public static void cacheTextures(ConfigureTexturesEvent event) {

        DEFAULT_SCENE_ATLAS = event.registerAtlas(GameClient.getInstance().identifierOf("default_scene_atlas"));
        UI_ATLAS = event.registerAtlas(GameClient.getInstance().identifierOf("ui_atlas"));

        event.addTexture(SMILE, DEFAULT_SCENE_ATLAS);
        event.addTexture(SAD, DEFAULT_SCENE_ATLAS);
        event.addTexture(SMILE_SMALL, DEFAULT_SCENE_ATLAS);
        event.addTexture(SAD_SMALL, DEFAULT_SCENE_ATLAS);
        event.addTexture(SMILE_TINY, DEFAULT_SCENE_ATLAS);
        event.addTexture(HAPPY, DEFAULT_SCENE_ATLAS, UI_ATLAS);
    }

}
