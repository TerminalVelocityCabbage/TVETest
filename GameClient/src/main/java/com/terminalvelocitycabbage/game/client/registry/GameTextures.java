package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.ConfigureTexturesEvent;

import static com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory.TEXTURE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;

public class GameTextures {

    //Texture Resources
    public static final Identifier SMILE = TEXTURE.identifierOf(GameClient.ID, "smile");
    public static final Identifier SAD = TEXTURE.identifierOf(GameClient.ID, "sad");
    public static final Identifier SMILE_SMALL = TEXTURE.identifierOf(GameClient.ID, "smile_small");
    public static final Identifier SMILE_TINY = TEXTURE.identifierOf(GameClient.ID, "smile_tiny");
    public static final Identifier SAD_SMALL = TEXTURE.identifierOf(GameClient.ID, "sad_small");
    public static final Identifier HAPPY = TEXTURE.identifierOf(GameClient.ID, "happy");
    public static final Identifier SCENE_FBO_TEXTURE = TEXTURE.identifierOf(GameClient.ID, "scene_fbo_texture");
    public static final Identifier POSITION_TEXTURE = TEXTURE.identifierOf(GameClient.ID, "position");
    public static final Identifier ALBEDO_TEXTURE = TEXTURE.identifierOf(GameClient.ID, "albedo");
    public static final Identifier NORMALS_TEXTURE = TEXTURE.identifierOf(GameClient.ID, "normals");
    public static final Identifier DEPTH_TEXTURE = TEXTURE.identifierOf(GameClient.ID, "depth");

    //Atlases
    public static Identifier DEFAULT_SCENE_ATLAS;
    public static Identifier UI_TEXTURE_ATLAS;

    public static void cacheTextures(ConfigureTexturesEvent event) {

        DEFAULT_SCENE_ATLAS = event.registerAtlas(GameClient.ID, "default_scene");
        UI_TEXTURE_ATLAS = event.registerAtlas(GameClient.ID, "ui_texture_atlas");

        event.addTexture(SMILE, DEFAULT_SCENE_ATLAS);
        event.addTexture(SAD, DEFAULT_SCENE_ATLAS);
        event.addTexture(SMILE_SMALL, DEFAULT_SCENE_ATLAS);
        event.addTexture(SAD_SMALL, DEFAULT_SCENE_ATLAS);
        event.addTexture(SMILE_TINY, DEFAULT_SCENE_ATLAS);
        event.addTexture(HAPPY, DEFAULT_SCENE_ATLAS, UI_TEXTURE_ATLAS);
        event.addRenderTexture(SCENE_FBO_TEXTURE, 200, 200);
        event.addRenderTexture(POSITION_TEXTURE, 800, 600, GL_RGBA32F, GL_RGBA, GL_FLOAT);
        event.addRenderTexture(ALBEDO_TEXTURE, 800, 600, GL_RGBA8, GL_RGBA, GL_UNSIGNED_BYTE);
        event.addRenderTexture(NORMALS_TEXTURE, 800, 600, GL_RGBA32F, GL_RGBA, GL_FLOAT);
        event.addRenderTexture(DEPTH_TEXTURE, 800, 600, GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT, GL_FLOAT);
    }

}
