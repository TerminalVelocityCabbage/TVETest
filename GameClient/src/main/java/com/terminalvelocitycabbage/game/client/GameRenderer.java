package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.renderer.RendererBase;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.debug.Log;
import org.lwjgl.bgfx.BGFXInit;
import org.lwjgl.glfw.GLFWNativeCocoa;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.glfw.GLFWNativeX11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;

import static org.lwjgl.bgfx.BGFX.*;

public class GameRenderer extends RendererBase {

    @Override
    public void init(WindowProperties properties, long windowHandle) {
        try (MemoryStack stack = MemoryStack.stackPush()) {

            //Init BGFX & Configure renderer properties from window properties
            BGFXInit init = BGFXInit.malloc(stack);

            //Set the window by platform instance
            switch (Platform.get()) {
                case LINUX -> init.platformData().ndt(GLFWNativeX11.glfwGetX11Display()).nwh(GLFWNativeX11.glfwGetX11Window(windowHandle));
                case MACOSX -> init.platformData().nwh(GLFWNativeCocoa.glfwGetCocoaWindow(windowHandle));
                case WINDOWS -> init.platformData().nwh(GLFWNativeWin32.glfwGetWin32Window(windowHandle));
            }

            bgfx_init_ctor(init);

            //Error if needed
            if (!bgfx_init(init)) Log.crash("Could not init bgfx for window " + windowHandle, new RuntimeException("Could not init BGFX error"));

            bgfx_reset(properties.getWidth(), properties.getHeight(), BGFX_RESET_VSYNC, init.resolution().format());

            //Log the renderer for debug purposes
            Log.info("BGFX Renderer Initialized with type:", bgfx_get_renderer_name(bgfx_get_renderer_type()));

            //Enable Debug Text
            //TODO enable this with a flag somewhere instead of it always being on
            bgfx_set_debug(BGFX_DEBUG_TEXT);

            //Set initial window color
            bgfx_set_view_clear(0, BGFX_CLEAR_COLOR, 0x303030ff, 1f, 0);
        }
    }

    @Override
    public void update(WindowProperties properties) {
        bgfx_set_view_clear(0, BGFX_CLEAR_COLOR, 0xff0000ff, 1f, (byte) 0);

        long encoder = bgfx_encoder_begin(false);
        bgfx_encoder_touch(encoder, 0);
        bgfx_encoder_end(encoder);

        bgfx_dbg_text_clear(0, false);
        bgfx_dbg_text_printf(0, 1, 0x1f, "Some text");
    }

    @Override
    public void destroy() {

    }

}
