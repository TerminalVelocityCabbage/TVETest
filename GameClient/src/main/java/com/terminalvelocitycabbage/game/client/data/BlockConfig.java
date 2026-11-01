package com.terminalvelocitycabbage.game.client.data;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.filesystem.resources.Resource;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.registry.GameResourceCategories;

public class BlockConfig {

    Cube[] cubes;

    public static BlockConfig of(Identifier blockConfigResource) {

        //Resource
        Resource resource = GameClient.getInstance().getFileSystem().getResource(GameResourceCategories.BLOCK, blockConfigResource);
        if (resource == null) {
            Log.crash("Could not find block resource: " + blockConfigResource);
            return null;
        }
        Config config = TomlFormat.instance().createParser().parse(resource.asString());

        //Parents
    }

    public BlockConfig(Cube[] cubes) {
        this.cubes = cubes;
    }

    public record Cube(
            int[] from,
            int[] to,
            CubeFace top,
            CubeFace bottom,
            CubeFace north,
            CubeFace south,
            CubeFace east,
            CubeFace west
    ) { }

    public record CubeFace(
            Identifier texture,
            boolean cull
    ) { }

}
