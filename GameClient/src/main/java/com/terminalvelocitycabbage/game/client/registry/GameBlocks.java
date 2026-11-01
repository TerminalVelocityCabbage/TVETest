package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.events.BlockConfigRegistrationEvent;
import com.terminalvelocitycabbage.game.common.GameCommon;

public class GameBlocks {

    static Identifier DIRT_BLOCK;
    static Identifier GRASS_BLOCK;

    public static void init(BlockConfigRegistrationEvent event) {
        DIRT_BLOCK = event.registerBlockConfig(from("dirt"));
        GRASS_BLOCK = event.registerBlockConfig(from("grass_block"));
    }

    private static Identifier from(String config) {
        return new Identifier(GameCommon.ID, "block", config);
    }

}
