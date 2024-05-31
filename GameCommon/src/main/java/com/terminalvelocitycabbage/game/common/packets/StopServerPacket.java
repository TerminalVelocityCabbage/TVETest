package com.terminalvelocitycabbage.game.common.packets;

import com.github.simplenet.Client;
import com.github.simplenet.Server;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.networking.SerializablePacket;
import com.terminalvelocitycabbage.engine.server.ServerBase;

public class StopServerPacket extends SerializablePacket {

    @Override
    public void interpretReceivedByClient(Client client) {
        Log.info("Server Stopped");
    }

    @Override
    public void interpretReceivedByServer(Server server, Client clientSender) {
        Log.info("Server Stopping...");
        ServerBase.getInstance().stop();
    }
}
