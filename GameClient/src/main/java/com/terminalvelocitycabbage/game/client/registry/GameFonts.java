package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.Font;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.FontRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameFonts {

    public static Identifier UCHRONY_FONT_RESOURCE;
    public static Identifier UCHRONY_FONT;

    public static void registerResources(ResourceRegistrationEvent event) {
        UCHRONY_FONT_RESOURCE = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.FONT, "UchronyScRegular.ttf").getIdentifier();
    }

    public static void registerFonts(FontRegistrationEvent event) {
        UCHRONY_FONT = event.register(new Font(UCHRONY_FONT_RESOURCE)).getIdentifier();
    }
}
