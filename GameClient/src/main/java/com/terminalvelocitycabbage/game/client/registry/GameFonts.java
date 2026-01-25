package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.Font;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.FontRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameFonts {

    private static Identifier LEXEND_FONT_RESOURCE;
    public static Identifier LEXEND_FONT;

    public static void registerResources(ResourceRegistrationEvent event) {
        LEXEND_FONT_RESOURCE = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.FONT, "Lexend.ttf").getIdentifier();
    }

    public static void registerFonts(FontRegistrationEvent event) {
        LEXEND_FONT = event.register(new Font(LEXEND_FONT_RESOURCE)).getIdentifier();
    }
}
