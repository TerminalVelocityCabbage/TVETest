package com.terminalvelocitycabbage.game.client.scenes;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.scene.Scene;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.graph.Routine;
import com.terminalvelocitycabbage.engine.registry.Identifier;

import java.util.List;

public class DefaultScene extends Scene {

    public DefaultScene(Identifier renderGraph, List<Routine> routines) {
        super(renderGraph, routines);
    }

    @Override
    public void init() {
        ClientBase.getInstance().getFileSystem().getResourceIdentifiersOfType(ResourceType.TEXTURE).forEach(identifier -> getTextureCache().createTexture(identifier));
    }

    @Override
    public void cleanup() {

    }
}
