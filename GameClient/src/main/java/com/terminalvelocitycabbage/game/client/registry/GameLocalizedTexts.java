package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.translation.Language;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.common.GameCommon;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameLocalizedTexts {

    //Language Files
    public static Identifier EN_US;
    public static Identifier ES;

    //Greetings
    public static final Identifier HELLO = register(GameCommon.ID, "greetings.hello");
    public static final Identifier GOODBYE = register(GameCommon.ID, "greetings.goodbye");
    //Miscellaneous
    public static final Identifier ANOTHER_TRANSLATION = register(GameCommon.ID, "misc.another_translation");
    public static final Identifier TEST = register(GameCommon.ID, "misc.test");

    private static Identifier register(String namespace, String path) {
        return ((GameClient) ClientBase.getInstance()).localizer.registerTranslatableText(namespace, path);
    }

    public static void init(ResourceRegistrationEvent event) {
        EN_US = registerLangResource(event, Language.ENGLISH_UNITED_STATES);
        ES = registerLangResource(event, Language.SPANISH_SPAIN);
    }

    //TODO make this common instead of client only (likely with a registry event)
    private static Identifier registerLangResource(ResourceRegistrationEvent event, Language language) {
        Identifier identifier = event.registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceCategory.LOCALIZATION, language.getAbbreviation() + ".toml").getIdentifier();
        Log.info("registered lang: " + identifier);
        return identifier;
    }

}
