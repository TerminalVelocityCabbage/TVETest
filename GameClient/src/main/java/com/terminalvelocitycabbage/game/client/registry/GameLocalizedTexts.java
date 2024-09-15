package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.translation.Language;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.common.GameCommon;

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

    public static void init() {
        EN_US = registerLangResource(Language.ENGLISH_UNITED_STATES);
        ES = registerLangResource(Language.SPANISH_SPAIN);
    }

    //TODO make this common instead of client only (likely with a registry event)
    private static Identifier registerLangResource(Language language) {
        return ClientBase.getInstance().getFileSystem().registerResource(GameClient.CLIENT_RESOURCE_SOURCE, ResourceType.LOCALIZATION, language.getAbbreviation() + ".toml").getIdentifier();
    }

}
