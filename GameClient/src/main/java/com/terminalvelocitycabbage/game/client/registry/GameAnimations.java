package com.terminalvelocitycabbage.game.client.registry;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.io.ConfigParser;
import com.electronwill.nightconfig.json.JsonFormat;
import com.terminalvelocitycabbage.engine.client.renderer.model.bedrock.BedrockAnimations;
import com.terminalvelocitycabbage.engine.client.renderer.model.bedrock.BedrockModelLoader;
import com.terminalvelocitycabbage.engine.filesystem.resources.Resource;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.LoadBedrockAnimationsEvent;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;

import java.util.HashMap;
import java.util.Map;

public class GameAnimations {

    public static Identifier MAIN_CHAR_ANIMATIONS;

    private static final Map<Identifier, BedrockAnimations> ANIMATION_CACHE = new HashMap<>();

    public static void registerResources(ResourceRegistrationEvent event) {
        MAIN_CHAR_ANIMATIONS = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.ANIMATION, "mainchar.animation.json").getIdentifier();
    }

    public static void cacheAnimations(LoadBedrockAnimationsEvent event) {
        ConfigParser<Config> parser = JsonFormat.minimalInstance().createParser();
        Resource resource = event.getFileSystem().getResource(ResourceCategory.ANIMATION, MAIN_CHAR_ANIMATIONS);
        var animations = BedrockModelLoader.loadAnimations(resource, parser);
        ANIMATION_CACHE.put(MAIN_CHAR_ANIMATIONS, animations);
    }

    public static BedrockAnimations getAnimations(Identifier identifier) {
        return ANIMATION_CACHE.get(identifier);
    }
}
