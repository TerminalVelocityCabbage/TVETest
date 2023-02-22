package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.debug.Log;

public class GameClient extends ClientBase {

    public static final String ID = "game";

    public GameClient(String namespace) {
        super(namespace, 50);
    }

    public static void main(String[] args) {
        GameClient client = new GameClient(ID);
        client.start();
    }

    @Override
    public void init() {
        super.init();
        setRenderer(new GameRenderer());
        getRenderer().init();
    }

    @Override
    public void destroy() {
    }

    @Override
    public void update() {
        super.update();
        getRenderer().update();
    }

    @Override
    public void tick() {
        Log.info("ticked");
    }
}