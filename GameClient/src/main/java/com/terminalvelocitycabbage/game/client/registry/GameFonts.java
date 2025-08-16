package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.GenerateFontsEvent;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameFonts {

    //Font Resources
    public static Identifier GETHO_FONT_RESOURCE;

    //Fonts
    public static Identifier GETHO_FONT_32;

    public static void registerResources(ResourceRegistrationEvent event) {
        //Register Fonts to the VFS
        GETHO_FONT_RESOURCE = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.FONT, "getho_bold.ttf").getIdentifier();
    }

    public static void generateFonts(GenerateFontsEvent event) {
        //Generate font data from resources for use later
        GETHO_FONT_32 = event.generateFont(GETHO_FONT_RESOURCE, 32);
    }

}
