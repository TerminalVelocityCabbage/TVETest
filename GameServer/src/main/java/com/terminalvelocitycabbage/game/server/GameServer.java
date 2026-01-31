package com.terminalvelocitycabbage.game.server;

import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceSource;
import com.terminalvelocitycabbage.engine.filesystem.sources.MainSource;
import com.terminalvelocitycabbage.engine.server.ServerBase;
import com.terminalvelocitycabbage.game.common.packets.StopServerPacket;
import com.terminalvelocitycabbage.templates.events.PacketRegistryEvent;
import com.terminalvelocitycabbage.templates.events.ResourceSourceRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.ServerLifecycleEvent;

public class GameServer extends ServerBase {

    public static final String ID = "game";

    public GameServer() {
        super(ID, 50);
        //Subscribe to relevant Events
        getEventDispatcher().listenToEvent(ServerLifecycleEvent.PRE_BIND, (event -> onPreBind((ServerLifecycleEvent) event)));
        getEventDispatcher().listenToEvent(ResourceSourceRegistrationEvent.EVENT, (event -> registerResourceSources((ResourceSourceRegistrationEvent) event)));
        getEventDispatcher().listenToEvent(PacketRegistryEvent.EVENT, event -> registerPackets((PacketRegistryEvent) event));
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }

    private void registerResourceSources(ResourceSourceRegistrationEvent event) {
        //Register and init filesystem things
        //Create resource sources for this server
        ResourceSource serverSource = new MainSource(this);
        //Define roots for these resources
        serverSource.registerDefaultSourceRoot(GameServer.ID, ResourceCategory.DEFAULT_CONFIG);
        //register this source to the filesystem
        event.registerResourceSource(getNamespace(), "server_main", serverSource);
    }

    private void registerPackets(PacketRegistryEvent event) {
        event.registerPacket(StopServerPacket.class);
    }

    @Override
    public void init() {
        super.init();

        Log.info("Starting Server...");

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
        super.tick();
        //Log.info("Game Server Ticked");
    }

    private void onPreBind(ServerLifecycleEvent event) {
        Log.info(event.getServer() + " is about be be bound!");
    }
}
