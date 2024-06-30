package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameTextures {

    public static void init(GameClient client, Identifier sourceIdentifier) {
        //Register Textures to the VFS
        client.getFileSystem().registerResource(sourceIdentifier, ResourceType.TEXTURE, "smile.png");
    }

    public static void cacheTextures(GameClient client) {
        //Get all textures and cache them on the texture cache
        client.getFileSystem().getResourceIdentifiersOfType(ResourceType.TEXTURE).forEach(identifier -> client.getTextureCache().createTexture(identifier));
    }

}
