package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.debug.Log;

public class GameClient extends ClientBase {

    public static final String ID = "game";

    public GameClient(String namespace) {
        super(namespace);
    }

    public static void main(String[] args) {
        GameClient client = new GameClient(ID);
        client.start();
    }

    @Override
    public void init() {
        Log.info("Game Client Started");
    }

    @Override
    public void destroy() {
        Log.info("Game Client Stopped");
    }

    @Override
    public void update() {
        Log.info("Game Client Updated");
    }

    @Override
    public void tick() {
        Log.info("Game Client Ticked");
    }
}