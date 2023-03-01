package com.terminalvelocitycabbage.game.server;

import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.event.HandleEvent;
import com.terminalvelocitycabbage.engine.server.ServerBase;
import com.terminalvelocitycabbage.game.common.StopServerPacket;
import com.terminalvelocitycabbage.templates.events.ServerLifecycleEvent;

public class GameServer extends ServerBase {

    public static final String ID = "game";

    public GameServer(String namespace) {
        super(namespace, 50);
        ServerBase.getInstance().subscribe(this);
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

    @HandleEvent(eventName = ServerLifecycleEvent.PRE_BIND)
    private void onPreBind(ServerLifecycleEvent event) {
        Log.info(event.getServer() + " is about be be bound!");
    }
}
