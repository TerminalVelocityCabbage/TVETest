package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ResourceCategoryRegistrationEvent;

public class GameResourceCategories {

    public static void registerResourceCategories(ResourceCategoryRegistrationEvent event) {
        ResourceCategory.registerEngineDefaults(event.getRegistry(), GameClient.ID);
    }
    
}
