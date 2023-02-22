package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.RendererBase;

import static org.lwjgl.opengl.GL11.*;

public class GameRenderer extends RendererBase {

    long lastTime = System.nanoTime();

    @Override
    public void init() {

    }

    //Temporary sample code
    @Override
    public void update() {

        int width = ClientBase.getInstance().getWindow().getWidth();
        int height = ClientBase.getInstance().getWindow().getHeight();

        glClear(GL_COLOR_BUFFER_BIT);
        glViewport(0, 0, width, height);

        long thisTime = System.nanoTime();
        float elapsed = (lastTime - thisTime) / 1E9f;
        lastTime = thisTime;

        float aspect = (float) width / height;
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-1.0f * aspect, aspect, -1.0f, +1.0f, -1.0f, +1.0f);

        glMatrixMode(GL_MODELVIEW);
        glRotatef(elapsed * 10.0f, 0, 0, 1);
        glBegin(GL_QUADS);
        glVertex2f(-0.5f, -0.5f);
        glVertex2f(+0.5f, -0.5f);
        glVertex2f(+0.5f, +0.5f);
        glVertex2f(-0.5f, +0.5f);
        glEnd();
    }

}
