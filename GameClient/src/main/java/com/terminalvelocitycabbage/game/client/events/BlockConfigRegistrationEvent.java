package com.terminalvelocitycabbage.game.client.events;

import com.terminalvelocitycabbage.engine.event.RegistryEvent;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.registry.Registry;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.data.BlockConfig;
import com.terminalvelocitycabbage.game.client.registry.GameResourceCategories;
import com.terminalvelocitycabbage.game.common.GameCommon;

public class BlockConfigRegistrationEvent extends RegistryEvent<BlockConfig> {

    public static final Identifier EVENT = new Identifier(GameCommon.ID, "event", "model_config_registration");

    public BlockConfigRegistrationEvent(Identifier name, Registry<BlockConfig> registry) {
        super(name, registry);
    }

    public Identifier registerBlockConfig(Identifier config) {
        return register(config, new BlockConfig(config)).getIdentifier();
    }
}
