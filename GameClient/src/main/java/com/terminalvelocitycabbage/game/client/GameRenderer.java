package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.renderer.RendererBase;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.debug.Log;
import org.lwjgl.bgfx.BGFX;
import org.lwjgl.bgfx.BGFXInit;
import org.lwjgl.glfw.GLFWNativeCocoa;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.glfw.GLFWNativeX11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;

import static org.lwjgl.bgfx.BGFX.*;

public class GameRenderer extends RendererBase {

    long lastTime = System.nanoTime();
    short frameBufferHandle;

    @Override
    public void init(WindowProperties properties, long windowHandle) {
        try (MemoryStack stack = MemoryStack.stackPush()) {

            //Init BGFX & Configure renderer properties from window properties
            BGFXInit init = BGFXInit.malloc(stack);
            bgfx_init_ctor(init);
            init.resolution(bgfxResolution -> bgfxResolution
                    .width(properties.getWidth())
                    .height(properties.getHeight())
                    .reset(BGFX_RESET_VSYNC) //TODO replace with a config apply()
            );

            //Set the window by platform instance
            switch (Platform.get()) {
                case LINUX -> init.platformData().ndt(GLFWNativeX11.glfwGetX11Display()).nwh(GLFWNativeX11.glfwGetX11Window(windowHandle));
                case MACOSX -> init.platformData().nwh(GLFWNativeCocoa.glfwGetCocoaWindow(windowHandle));
                case WINDOWS -> init.platformData().nwh(GLFWNativeWin32.glfwGetWin32Window(windowHandle));
            }

            //Error if needed
            if (!bgfx_init(init)) Log.crash("Could not init bgfx for window " + windowHandle, new RuntimeException("Could not init BGFX error"));
        }

        //Log the renderer for debug purposes
        Log.debug("BGFX Renderer Initialized", bgfx_get_renderer_name(bgfx_get_renderer_type()));

        //Enable Debug Text
        //TODO enable this with a flag somewhere instead of it always being on
        bgfx_set_debug(BGFX_DEBUG_TEXT);

        //Set initial window color
        bgfx_set_view_clear(getRendererId(), BGFX_CLEAR_COLOR, 0x303030ff, 1f, 0);
        frameBufferHandle = bgfx_create_frame_buffer_from_nwh(windowHandle, properties.getWidth(), properties.getHeight(), BGFX_TEXTURE_FORMAT_RGBA16, BGFX_TEXTURE_FORMAT_UNKNOWN_DEPTH);
        bgfx_set_view_frame_buffer(properties.getRendererId(), frameBufferHandle);
    }

    //Temporary sample code
    @Override
    public void update(WindowProperties properties) {

        //long thisTime = System.nanoTime();

        long encoder = bgfx_encoder_begin(false);
        bgfx_encoder_touch(encoder, getRendererId());
        bgfx_encoder_end(encoder);

        //float elapsed = (lastTime - thisTime) / 1E9f;
        //lastTime = thisTime;
    }

    @Override
    public void destroy() {

    }

}
