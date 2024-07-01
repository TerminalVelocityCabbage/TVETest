package com.terminalvelocitycabbage.game.client.registry;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.ConfigWriter;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.terminalvelocitycabbage.engine.config.TVConfig;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.filesystem.resources.Resource;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;

public class GameTests {

    public static void run(GameClient client) {
        //testFileSystemRegistryStuff(client);
        //testNightConfig(client);
    }

    //Test that this game has access to both itself and mods on its filesystem
    // (it will never be accessed like this but is good to show it's possible for the future)
    public static void testFileSystemRegistryStuff(GameClient client) {

        //List resources which are registered
        //getFileSystem().listResources();

        //Test reading the string that is the config file from this game
        Resource resource = client.getFileSystem().getResource(ResourceType.DEFAULT_CONFIG, new Identifier("game", "test.toml"));
        Log.info(resource.asString());

        //Test reading the string that is the mod config file (this is the part that will never work like this)
        Resource modResource = client.getFileSystem().getResource(ResourceType.DEFAULT_CONFIG, new Identifier("testmod", "testmod.toml"));
        Log.info(modResource.asString());
    }

    //TVE implements a few utilities on top of Night Config, we test those here
    public static void testNightConfig(GameClient client) {
        //Test night config stuff
        FileConfig config = TVConfig.getOrCreateFileConfig(client.getFileSystem(), new Identifier("game", "test.toml"));
        config.load();
        ConfigFormat<CommentedConfig> tomlFormat = TomlFormat.instance();
        ConfigWriter tomlWriter = tomlFormat.createWriter();
        String toml = tomlWriter.writeToString(config);
        Log.info(toml);
    }

}
