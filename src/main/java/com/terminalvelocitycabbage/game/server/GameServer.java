package com.terminalvelocitycabbage.game.server;

import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.server.ServerBase;

public class GameServer extends ServerBase {

    public static final String ID = "game";

    public GameServer(String namespace) {
        super(namespace);
    }

    public static void main(String[] args) {
        GameServer server = new GameServer(ID);
        server.start();
    }

    @Override
    public void init() {
        Log.info("Game Server Started");
    }

    @Override
    public void destroy() {
        Log.info("Game Server Stopped");
    }

    @Override
    public void tick() {
        Log.info("Game Server Ticked");
    }
}
