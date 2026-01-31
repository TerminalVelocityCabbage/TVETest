package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceSource;
import com.terminalvelocitycabbage.engine.filesystem.sources.MainSource;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.common.GameCommon;
import com.terminalvelocitycabbage.templates.events.ResourceCategoryRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.ResourceSourceRegistrationEvent;

public class GameResources {

    public static Identifier CLIENT_RESOURCE_SOURCE;

    public static void registerResourceCategories(ResourceCategoryRegistrationEvent event) {
        ResourceCategory.registerEngineDefaults(event.getRegistry(), GameClient.ID);
    }

    public static void registerResourceSources(ResourceSourceRegistrationEvent event) {

        //Register and init filesystem things
        //Create resource sources for this client
        ResourceSource clientSource = new MainSource(GameClient.getInstance());
        //Define roots for these resources
        clientSource.registerDefaultSources(GameCommon.ID);
        //register this source
        CLIENT_RESOURCE_SOURCE = event.registerResourceSource(GameClient.ID, "client_main", clientSource);
    }
    
}
