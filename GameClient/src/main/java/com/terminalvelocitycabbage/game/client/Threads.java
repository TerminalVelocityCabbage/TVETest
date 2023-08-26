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

    public static void main(String[] args) {
        WindowManager windowManager = new WindowManager();

        windowManager.init();

        windowManager.createNewWindow("initial window");

        windowManager.loop();

        windowManager.destroy();
    }

    public static class WindowManager {

        List<Long> windowsToDestroy = Collections.synchronizedList(new ArrayList<>());

        int scaleX;

        Map<Long, GLFWThread> threads = new HashMap<>();

        Map<Long, CountDownLatch> exitList = new HashMap<>();

        public WindowManager() { }

        private boolean hasAliveWindow() {
            for (CountDownLatch countDownLatch : exitList.values()) {
                if (countDownLatch.getCount() > 0) return true;
            }
            return false;
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
            while (true) {

                windowsToDestroy.forEach(window -> {
                    System.out.println("Made it to the end of life of a window");
                    destroyWindow(window);
                    System.out.println("destroyed window " + window);
                });
                windowsToDestroy.clear();

                glfwWaitEvents();

                if (!hasAliveWindow()) break;

                for (long window: threads.keySet()) {
                    if (glfwWindowShouldClose(window)) {
                        exitList.get(window).countDown();
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
            //Prevent an IllegalStateException on last destroyed window
            if (threads.size() > 1 && exitList.size() > 1) {
                threads.remove(window);
                exitList.remove(window);
            }
        }
    }

    private static class GLFWThread extends Thread {

        final long window;

        CountDownLatch quit;
        WindowManager manager;

        GLFWThread(long window, int index, CountDownLatch quit, WindowManager windowManager) {
            this.window = window;

            System.out.println("Created GLFWThread: window:" + window);

            this.quit = quit;
            this.manager = windowManager;
        }

        @Override
        public void run() {
            glfwMakeContextCurrent(window);
            GL.createCapabilities();

            glfwSwapInterval(1);

            while (quit.getCount() != 0) {
                float r = (float)Math.abs(Math.sin(glfwGetTime() * 2f + 15));
                float g = (float)Math.abs(Math.sin(glfwGetTime() * 2f + 12345));
                float b = (float)Math.abs(Math.sin(glfwGetTime() * 2f));
                glClearColor(r, g, b, 0f);
                glClear(GL_COLOR_BUFFER_BIT);
                glfwSwapBuffers(window);
            }

            //queue this window for destruction
            manager.windowsToDestroy.add(window);

            GL.setCapabilities(null);
        }
    }
}

