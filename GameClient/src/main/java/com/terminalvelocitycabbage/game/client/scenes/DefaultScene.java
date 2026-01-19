package com.terminalvelocitycabbage.game.client.scenes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.model.MeshCache;
import com.terminalvelocitycabbage.engine.client.renderer.model.bedrock.AnimationComponent;
import com.terminalvelocitycabbage.engine.client.renderer.model.bedrock.BedrockMesh;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import org.joml.Matrix4f;
import com.terminalvelocitycabbage.game.client.registry.GameAnimations;
import com.terminalvelocitycabbage.game.client.registry.GameEntities;
import com.terminalvelocitycabbage.game.client.registry.GameMeshes;
import com.terminalvelocitycabbage.game.client.registry.GameTextures;

import java.util.HashMap;
import java.util.List;

public class DefaultScene extends Scene {

    private Entity playerEntity;
    private Entity mainCharEntity;

    public DefaultScene(Identifier renderGraph, Routine... routines) {
        super(renderGraph, List.of(routines));
    }

    @Override
    public void init() {
        GameClient client = (GameClient) ClientBase.getInstance();
        Manager manager = client.getManager();

        client.getTextureCache().generateAtlas(GameTextures.DEFAULT_SCENE_ATLAS);
        setMeshCache(new MeshCache(client.getModelRegistry(), client.getMeshRegistry(), client.getTextureCache()));

        manager.createEntityFromTemplate(GameEntities.SMILE_SQUARE_ENTITY);
        manager.createEntityFromTemplate(GameEntities.SAD_SQUARE_ENTITY);
        playerEntity = manager.createEntityFromTemplate(GameEntities.PLAYER_ENTITY);

        mainCharEntity = manager.createEntityFromTemplate(GameEntities.MAIN_CHAR_ENTITY);
        var animComp = mainCharEntity.getComponent(AnimationComponent.class);
        animComp.animations = GameAnimations.getAnimations(GameAnimations.MAIN_CHAR_ANIMATIONS);

        var mesh = (BedrockMesh) client.getMeshRegistry().get(GameMeshes.MAIN_CHAR_MESH).getDataMesh();
        animComp.geometry = mesh.getGeometry();
        animComp.boneIndexMap = new HashMap<>();
        var geoData = animComp.geometry.minecraft_geometry.get(0);
        for (int i = 0; i < geoData.bones.size(); i++) {
            animComp.boneIndexMap.put(geoData.bones.get(i).name, i);
        }

        if (!animComp.animations.animations.isEmpty()) {
            animComp.currentAnimation = animComp.animations.animations.keySet().iterator().next();
            animComp.playing = true;
        }
    }

    @Override
    public void cleanup() {
        GameClient.getInstance().getTextureCache().cleanupAtlas(GameTextures.DEFAULT_SCENE_ATLAS);
        getMeshCache().cleanup();
    }
}
