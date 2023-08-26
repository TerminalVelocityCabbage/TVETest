package com.terminalvelocitycabbage.game.client;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.*;
import java.util.concurrent.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public final class Threads {

    private static final float[][] rgb    = {
            {1f, 0f, 0f, 0},
            {0f, 1f, 0f, 0},
            {0f, 0f, 1f, 0}
    };

    public static void main(String[] args) {
        WindowManager windowManager = new WindowManager();

        windowManager.init();

        windowManager.createNewWindow("initial window");

        windowManager.loop();

        windowManager.destroy();
    }

    public static class WindowManager {

        int scaleX;

        Map<Long, GLFWThread> threads = new HashMap<>();

        Map<Long, CountDownLatch> exitList = new HashMap<>();

        public WindowManager() { }

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

                for (long window: threads.keySet()) {
                    if (glfwWindowShouldClose(window)) {
                        exitList.get(window).countDown();
                        break out;
                    }
                }
            }
        }

        private void destroy() {

            threads.forEach((window, glfwThread) -> {
                glfwThread.quit.countDown();
            });

            List<Boolean> threadStates;
            int numAlive;
            do {
                numAlive = 0;
                threadStates = threads.values().stream().map(Thread::isAlive).toList();
                for (int i = 0; i < threadStates.size(); i++) {
                    if (threadStates.get(i)) numAlive++;
                }
            } while (numAlive != 0);

            glfwTerminate();
            Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        }

        private void createNewWindow(String title) {
            long window = glfwCreateWindow(200, 200, title, NULL, NULL);
            if (window == NULL) {
                throw new IllegalStateException("Failed to create GLFW window.");
            }

            exitList.put(window, new CountDownLatch(1));
            threads.put(window, new GLFWThread(window, threads.size() % 3, exitList.get(window), this));

            glfwSetKeyCallback(window, (windowHnd, key, scancode, action, mods) -> {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(windowHnd, true);
                }
                if (key == GLFW_KEY_1 && action == GLFW_RELEASE) {
                    createNewWindow("Window created from " + title);
                }
            });
            glfwSetWindowPos(window, (200 * scaleX + 50), 200);
            glfwShowWindow(window);

            threads.get(window).start();
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
        WindowManager manager;

        GLFWThread(long window, int index, CountDownLatch quit, WindowManager windowManager) {
            this.window = window;

            this.index = index;

            this.r = rgb[index][0];
            this.g = rgb[index][1];
            this.b = rgb[index][2];

            System.out.println("Created GLFWThread: window:" + window + ", rgb: (" + r + ", " + g + ", " + b + ")");

            this.quit = quit;
            this.manager = windowManager;
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

            System.out.println("Made it to the end of life of a window");
            manager.destroyWindow(window);
            System.out.println("destroyed window " + window);
            GL.setCapabilities(null);
        }
    }
}

