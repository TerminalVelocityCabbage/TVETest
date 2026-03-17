package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.FontRegistrationEvent;

import static com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory.FONT;

public class GameFonts {

    public static Identifier LEXEND_FONT;

    public static void registerFonts(FontRegistrationEvent event) {
        LEXEND_FONT = event.registerFont(FONT.identifierOf(GameClient.ID, "Lexend"));
    }
}
