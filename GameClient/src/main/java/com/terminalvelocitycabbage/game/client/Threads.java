package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.Window;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * GLFW demo that showcases rendering to multiple windows from multiple threads. Ported from the GLFW
 * <a href="https://github.com/glfw/glfw/blob/master/tests/threads.c">threads</a> test.
 *
 * @author Brian Matzon <brian@matzon.dk>
 */
public final class Threads {

    private Threads() {
    }

    private static final float[][] rgb    = {
            {1f, 0f, 0f, 0},
            {0f, 1f, 0f, 0},
            {0f, 0f, 1f, 0}
    };

    public static void main(String[] args) {
        WindowManager windowManager = new WindowManager();

        windowManager.init();

        windowManager.createNewWindow("initial window");

        windowManager.start();
    }

    public static class WindowManager {
        private final List<String> titles = new ArrayList<>();

        int scaleX;

        List<GLFWThread> threads = new ArrayList<>();

        CountDownLatch quit = new CountDownLatch(1);

        public WindowManager() { }

        private void start() {
            loop();
            destroy();
        }

        public void init() {
            GLFWErrorCallback.createPrint().set();
            if (!glfwInit()) {
                throw new IllegalStateException("Failed to initialize GLFW.");
            }

            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE);
            if (Platform.get() == Platform.MACOSX) {
                glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW_FALSE);
            }

            try (MemoryStack s = stackPush()) {
                FloatBuffer px = s.mallocFloat(1);

                glfwGetMonitorContentScale(glfwGetPrimaryMonitor(), px, null);

                scaleX = (int)px.get(0);
            }
        }

        private void loop() {
            out:
            while (true) {
                glfwWaitEvents();

                for (int i = 0; i < titles.size(); i++) {
                    if (glfwWindowShouldClose(threads.get(i).window)) {
                        quit.countDown();
                        break out;
                    }
                }
            }
        }

        private void destroy() {
            for (int i = 0; i < threads.size(); i++) {
                try {
                    threads.get(i).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < threads.size(); i++) {
                destroyWindow(threads.get(i).window);
            }

            glfwTerminate();
            Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        }

        private void createNewWindow(String title) {
            long window = glfwCreateWindow(200, 200, title, NULL, NULL);
            if (window == NULL) {
                throw new IllegalStateException("Failed to create GLFW window.");
            }

            glfwSetKeyCallback(window, (windowHnd, key, scancode, action, mods) -> {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(windowHnd, true);
                }
                if (key == GLFW_KEY_1 && action == GLFW_RELEASE) {
                    createNewWindow("Window created from " + title);
                }
            });
            glfwSetWindowPos(window, 200 + titles.size() * (200 * scaleX + 50), 200);
            glfwShowWindow(window);

            threads.add(new GLFWThread(window, threads.size() % 3, quit));
            threads.get(threads.size() - 1).start();
        }

        private void destroyWindow(long window) {
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        }
    }

    private static class GLFWThread extends Thread {

        final long window;

        final int   index;
        final float r, g, b;

        CountDownLatch quit;

        GLFWThread(long window, int index, CountDownLatch quit) {
            this.window = window;

            this.index = index;

            this.r = rgb[index][0];
            this.g = rgb[index][1];
            this.b = rgb[index][2];

            System.out.println("GLFWThread: window:" + window + ", rgb: (" + r + ", " + g + ", " + b + ")");

            this.quit = quit;
        }

        @Override
        public void run() {
            glfwMakeContextCurrent(window);
            GL.createCapabilities();

            glfwSwapInterval(1);

            while (quit.getCount() != 0) {
                float v = (float)Math.abs(Math.sin(glfwGetTime() * 2f));
                glClearColor(r * v, g * v, b * v, 0f);
                glClear(GL_COLOR_BUFFER_BIT);
                glfwSwapBuffers(window);
            }
            GL.setCapabilities(null);
        }
    }
}

