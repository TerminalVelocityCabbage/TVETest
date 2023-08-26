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
        //Create a new Window Manager to (you guessed it) manage our multiple windows
        WindowManager windowManager = new WindowManager();

        //Init window manager to get glwf ready to do things
        windowManager.init();

        //Create our first window (we can do this as many times as we want and from anywhere)
        windowManager.createNewWindow("initial window");

        //Start the window loop, this will run until there are no more windows left or this manager is destroyed
        //(preferably the former option)
        windowManager.loop();

        //Cleans up the window manager and glwf contexts
        windowManager.destroy();
    }

    public static class WindowManager {

        //Stores a list of window handles that need to be destroyed on the main thread
        List<Long> windowsToDestroy = Collections.synchronizedList(new ArrayList<>());

        //Some monitor info (this should be expanded in the future)
        int scaleX;

        //The (usually) active windows of this manager
        Map<Long, GLFWThread> threads = new HashMap<>();

        //Returns true if this window manager has any alive windows
        private boolean hasAliveWindow() {
            for (GLFWThread thread : threads.values()) {
                if (!thread.quit) return true;
            }
            return false;
        }

        //Initialize this window manager (glfw)
        public void init() {

            //Init the glfw error callback
            GLFWErrorCallback.createPrint().set();
            //Throw an error if glfw fails to init
            if (!glfwInit()) {
                throw new IllegalStateException("Failed to initialize GLFW.");
            }

            //configure glfw
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE);
            if (Platform.get() == Platform.MACOSX) {
                glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW_FALSE);
            }

            //get monitor content scale (this should be expanded)
            try (MemoryStack s = stackPush()) {
                FloatBuffer px = s.mallocFloat(1);
                glfwGetMonitorContentScale(glfwGetPrimaryMonitor(), px, null);
                scaleX = (int)px.get(0);
            }
        }

        //The actual window update loop
        private void loop() {
            while (true) {

                //Destroy all destroyable windows before polling for events
                windowsToDestroy.forEach(window -> {
                    System.out.println("Made it to the end of life of a window");
                    destroyWindow(window);
                    System.out.println("destroyed window " + window);
                });
                //Reset for the next loop
                windowsToDestroy.clear();

                //Poll for window events (like input or closing etc.)
                glfwWaitEvents();

                //Don't update the threads if there is nothing to update
                if (!hasAliveWindow()) break;

                //Check for window close requests
                threads.forEach((window, glfwThread) -> {
                    if (glfwWindowShouldClose(window)) glfwThread.quit = true;
                });
            }
        }

        //Destroys this window manager and glfw context
        private void destroy() {

            //Mark all windows as needing to quit
            threads.forEach((window, glfwThread) -> {
                glfwThread.quit = true;
            });

            //wait for all window threads to die by updating this list of boolean states until no more are true
            List<Boolean> threadStates;
            int numAlive;
            do {
                numAlive = 0;
                threadStates = threads.values().stream().map(Thread::isAlive).toList();
                for (int i = 0; i < threadStates.size(); i++) {
                    if (threadStates.get(i)) numAlive++;
                }
            } while (numAlive != 0);

            //Terminate this glfw instance
            glfwTerminate();
            //Free the error callback
            Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        }

        //Create a new window in this manager
        private void createNewWindow(String title) {
            //Create the glfw window
            long window = glfwCreateWindow(200, 200, title, NULL, NULL);
            //Error if the window is not created successfully
            if (window == NULL) {
                throw new IllegalStateException("Failed to create GLFW window.");
            }

            //Add this window to the list of active window threads
            threads.put(window, new GLFWThread(window, this));

            //Setup the key callbacks
            //TODO point this to an input handler
            glfwSetKeyCallback(window, (windowHnd, key, scancode, action, mods) -> {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(windowHnd, true);
                }
                if (key == GLFW_KEY_1 && action == GLFW_RELEASE) {
                    createNewWindow("Window created from " + title);
                }
            });

            //Set the window position to offset from the corner
            glfwSetWindowPos(window, (200 * scaleX + 50), 200);

            //Show the window
            glfwShowWindow(window);

            //Start the window update loop
            threads.get(window).start();
        }

        //Destroys the specified window
        //This MUST always be called from the main thread
        private void destroyWindow(long window) {
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
            //Prevent an IllegalStateException on last destroyed window
            if (threads.size() > 1) {
                threads.remove(window);
            }
        }
    }

    private static class GLFWThread extends Thread {

        //The memory handle for this window (glfw)
        final long window;

        //Marks whether this window should be closed or not based on some user input (usually clicking the x)
        boolean quit;
        //The window manager that this window belongs too
        WindowManager manager;

        GLFWThread(long window, WindowManager windowManager) {
            this.window = window;
            this.quit = false;
            this.manager = windowManager;
        }

        @Override
        public void run() {
            //make this thread use this context for this new window
            glfwMakeContextCurrent(window);
            GL.createCapabilities();

            //Turn on vsync
            //TODO swap this out for a window config apply()
            glfwSwapInterval(1);

            //render some pretty colors to the window
            //TODO swap this out for the renderer
            while (!quit) {
                float r = (float)Math.abs(Math.sin(glfwGetTime() * 2f + 15));
                float g = (float)Math.abs(Math.sin(glfwGetTime() * 2f + 12345));
                float b = (float)Math.abs(Math.sin(glfwGetTime() * 2f));
                glClearColor(r, g, b, 0f);
                glClear(GL_COLOR_BUFFER_BIT);
                glfwSwapBuffers(window);
            }

            //queue this window for destruction
            manager.windowsToDestroy.add(window);

            //Clear the gl capabilities from this window
            GL.setCapabilities(null);
        }
    }
}

