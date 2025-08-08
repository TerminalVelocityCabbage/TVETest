package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.translation.Language;
import com.terminalvelocitycabbage.game.common.GameCommon;
import com.terminalvelocitycabbage.templates.events.LocalizedTextKeyRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

public class GameLocalizedTexts {

    //Language Files
    public static Identifier EN_US;
    public static Identifier ES;

    //Greetings
    public static Identifier HELLO;
    public static Identifier GOODBYE;
    //Miscellaneous
    public static Identifier ANOTHER_TRANSLATION;
    public static Identifier TEST;

    public static void registerLocalizedTextKeys(LocalizedTextKeyRegistrationEvent event) {
        HELLO = event.registerKey(GameCommon.ID, "greetings.hello").getIdentifier();
        GOODBYE = event.registerKey(GameCommon.ID, "greetings.goodbye").getIdentifier();
        ANOTHER_TRANSLATION = event.registerKey(GameCommon.ID, "misc.another_translation").getIdentifier();
        TEST = event.registerKey(GameCommon.ID, "misc.test").getIdentifier();
    }

    public static void registerTranslationResources(ResourceRegistrationEvent event) {
        EN_US = registerLangResource(event, Language.ENGLISH_UNITED_STATES);
        ES = registerLangResource(event, Language.SPANISH_SPAIN);
    }

    private static Identifier registerLangResource(ResourceRegistrationEvent event, Language language) {
        return event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.LOCALIZATION, language.getAbbreviation() + ".toml").getIdentifier();
    }

}
