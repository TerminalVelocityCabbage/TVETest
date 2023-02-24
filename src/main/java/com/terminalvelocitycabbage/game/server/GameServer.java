package com.terminalvelocitycabbage.game.server;

import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.server.ServerBase;
import com.terminalvelocitycabbage.game.common.StopServerPacket;

public class GameServer extends ServerBase {

    public static final String ID = "game";

    public GameServer(String namespace) {
        super(namespace, 50);
    }

    public static void main(String[] args) {
        GameServer server = new GameServer(ID);
        server.start();
    }

    @Override
    public void init() {
        super.init();

        Log.info("Starting Server...");

        //Register Packets
        getPacketRegistry().registerPacket(StopServerPacket.class);

        //Configure connection
        setAddress("127.0.0.1");
        setPort(4132);

        Log.info("Game Server Started");
    }

    @Override
    public void destroy() {
        super.destroy();

        Log.info("Game Server Stopped");
    }

    @Override
    public void tick() {
        //Log.info("Game Server Ticked");
    }
}
