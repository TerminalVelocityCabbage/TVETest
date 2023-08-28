package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.renderer.RendererBase;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import org.lwjgl.opengl.GL11;

public class GameRenderer extends RendererBase {

    long lastTime = System.nanoTime();

    @Override
    public void init() {

    }

    //Temporary sample code
    @Override
    public void update(WindowProperties window) {

        int width = window.getWidth();
        int height = window.getHeight();

        GL11.glClearColor(0, 0, 0.5f, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glViewport(0, 0, width, height);

        long thisTime = System.nanoTime();
        float elapsed = (lastTime - thisTime) / 1E9f;
        lastTime = thisTime;

        float aspect = (float) width / height;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(-1.0f * aspect, aspect, -1.0f, +1.0f, -1.0f, +1.0f);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glRotatef(elapsed * 10.0f, 0, 0, 1);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-0.5f, -0.5f);
        GL11.glVertex2f(+0.5f, -0.5f);
        GL11.glVertex2f(+0.5f, +0.5f);
        GL11.glVertex2f(-0.5f, +0.5f);
        GL11.glEnd();
    }

}
