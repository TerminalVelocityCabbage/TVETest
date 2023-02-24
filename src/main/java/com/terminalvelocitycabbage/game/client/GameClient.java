package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.game.common.StopServerPacket;

import static org.lwjgl.glfw.GLFW.*;

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

        connect("127.0.0.1", 4132);
    }

    @Override
    public void destroy() {
        //Close the client connection
        disconnect();
    }

    @Override
    public void update() {
        super.update();
        getRenderer().update();
    }

    @Override
    public void tick() {
        //Log.info("ticked");
    }

    @Override
    public void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, true);
        if (key == GLFW_KEY_S && action == GLFW_RELEASE) sendPacket(new StopServerPacket(), StopServerPacket.class);
    }

}