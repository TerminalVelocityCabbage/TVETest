package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameTextures {

    public static Identifier SMILE;

    public static void init(GameClient client, Identifier sourceIdentifier) {
        //Register Textures to the VFS
        SMILE = client.getFileSystem().registerResource(sourceIdentifier, ResourceType.TEXTURE, "smile.png");
    }

}
