package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceSource;
import com.terminalvelocitycabbage.engine.filesystem.sources.MainSource;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ResourceCategoryRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.ResourceSourceRegistrationEvent;

public class GameResources {

    public static Identifier CLIENT_RESOURCE_SOURCE;

    public static void registerResourceCategories(ResourceCategoryRegistrationEvent event) {
        ResourceCategory.registerEngineDefaults(event.getRegistry(), GameClient.ID);
    }

    public static void registerResourceSources(ResourceSourceRegistrationEvent event) {

        var client = GameClient.getInstance();
        //Register and init filesystem things
        //Create resource sources for this client
        ResourceSource clientSource = new MainSource(GameClient.ID, client);
        //Define roots for these resources
        clientSource.registerDefaultSources();
        //register this source
        CLIENT_RESOURCE_SOURCE = event.register(client.identifierOf("client_main_resource_source"), clientSource).getIdentifier();
    }
    
}
