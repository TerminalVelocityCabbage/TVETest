package com.terminalvelocitycabbage.game.client;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.ConfigWriter;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.graph.RenderGraph;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.config.TVConfig;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.filesystem.resources.Resource;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceSource;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.filesystem.sources.MainSource;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.touples.Pair;
import com.terminalvelocitycabbage.game.client.renderer.SpinningSquareRenderer;
import com.terminalvelocitycabbage.game.client.renderer.node.OppositeSpinningSquareRenderNode;
import com.terminalvelocitycabbage.game.client.renderer.node.SpinningSquareRenderNode;
import com.terminalvelocitycabbage.game.common.StopServerPacket;

import static org.lwjgl.glfw.GLFW.*;

public class GameClient extends ClientBase {

    public static final String ID = "game";

    public GameClient() {
        super(ID, 50);
    }

    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.start();
    }

    @Override
    public void preInit() {
        super.preInit();

        //Register and init filesystem things
        //Create resource sources for this client
        ResourceSource clientSource = new MainSource(ID, this);
        Identifier sourceIdentifier = identifierOf("client_main_resource_source");
        //Define roots for these resources
        clientSource.registerDefaultSourceRoot(ResourceType.MODEL);
        clientSource.registerDefaultSourceRoot(ResourceType.TEXTURE);
        clientSource.registerDefaultSourceRoot(ResourceType.ANIMATION);
        clientSource.registerDefaultSourceRoot(ResourceType.SHADER);
        clientSource.registerDefaultSourceRoot(ResourceType.SOUND);
        clientSource.registerDefaultSourceRoot(ResourceType.FONT);
        clientSource.registerDefaultSourceRoot(ResourceType.DEFAULT_CONFIG);
        //register this source to the filesystem
        getFileSystem().registerResourceSource(sourceIdentifier, clientSource);

        //Register resources
        getFileSystem().registerResource(sourceIdentifier, ResourceType.DEFAULT_CONFIG, "test.toml");

        RenderGraph spinningSquareRenderGraph = RenderGraph.builder()
                .addNode(identifierOf("spinningSquare"), SpinningSquareRenderNode.class)
                .build();
        RenderGraph oppositeSpinningSquareRenderGraph = RenderGraph.builder()
                .addNode(identifierOf("oppositeSpinningSquare"), OppositeSpinningSquareRenderNode.class)
                .build();

        //Register renderers
        getRendererRegistry().register(identifierOf("game"), new Pair<>(SpinningSquareRenderer.class, spinningSquareRenderGraph));
        getRendererRegistry().register(identifierOf("game2"), new Pair<>(SpinningSquareRenderer.class, oppositeSpinningSquareRenderGraph));

        //Setup this Client's Routine
        //routine = Routine.builder()
        //        .addNode(new Identifier(ID, "updateSchedules"), UpdateScheduleSystem.class)
        //        .build();
    }

    @Override
    public void init() {
        super.init();

        //Create windows based on some initial properties
        WindowProperties defaultWindow = new WindowProperties(600, 400, "initial window", identifierOf("game"));
        WindowProperties secondWindow = new WindowProperties(600, 400, "second window", identifierOf("game2"));
        getWindowManager().createNewWindow(defaultWindow);
        getWindowManager().createNewWindow(secondWindow);

        getFileSystem().init();
        modInit();

        connect("127.0.0.1", 4132);

        //Test
        //testFileSystemRegistryStuff();
        //testNightConfig();
    }

    @Override
    public void destroy() {
        super.destroy();
        //Close the client connection
        disconnect();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, true);
        if (key == GLFW_KEY_S && action == GLFW_RELEASE) sendPacket(new StopServerPacket(), StopServerPacket.class);
        if (key == GLFW_KEY_1 && action == GLFW_RELEASE) {
            var properties = getWindowManager().getPropertiesFromWindow(window);
            ClientBase.getInstance().getWindowManager().createNewWindow(new WindowProperties(properties).setTitle("Window created from " + properties.getTitle()));
        }
    }

    //Test that this game has access to both itself and mods on its filesystem
    // (it will never be accessed like this but is good to show it's possible for the future)
    public void testFileSystemRegistryStuff() {

        //List resources which are registered
        //getFileSystem().listResources();

        //Test reading the string that is the config file from this game
        Resource resource = ClientBase.getInstance().getFileSystem().getResource(ResourceType.DEFAULT_CONFIG, new Identifier("game", "test.toml"));
        Log.info(resource.asString());

        //Test reading the string that is the mod config file (this is the part that will never work like this)
        Resource modResource = ClientBase.getInstance().getFileSystem().getResource(ResourceType.DEFAULT_CONFIG, new Identifier("testmod", "testmod.toml"));
        Log.info(modResource.asString());
    }

    //TVE implements a few utilities on top of Night Config, we test those here
    public void testNightConfig() {
        //Test night config stuff
        FileConfig config = TVConfig.getOrCreateFileConfig(ClientBase.getInstance().getFileSystem(), new Identifier("game", "test.toml"));
        config.load();
        ConfigFormat<CommentedConfig> tomlFormat = TomlFormat.instance();
        ConfigWriter tomlWriter = tomlFormat.createWriter();
        String toml = tomlWriter.writeToString(config);
        Log.info(toml);
    }

}