package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.translation.Language;
import com.terminalvelocitycabbage.game.common.GameCommon;
import com.terminalvelocitycabbage.templates.events.LocalizedTextKeyRegistrationEvent;

import static com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory.LOCALIZATION;

public class GameLocalizedTexts {

    //Language Files
    public static final Identifier EN_US = LOCALIZATION.identifierOf(GameCommon.ID, Language.ENGLISH_UNITED_STATES.getAbbreviation());
    public static final Identifier ES = LOCALIZATION.identifierOf(GameCommon.ID, Language.SPANISH_SPAIN.getAbbreviation());

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

}
