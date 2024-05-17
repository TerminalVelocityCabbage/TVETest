package com.terminalvelocitycabbage.game.client;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.ConfigWriter;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.input.control.*;
import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.types.GamepadInput;
import com.terminalvelocitycabbage.engine.client.input.types.KeyboardInput;
import com.terminalvelocitycabbage.engine.client.input.types.MouseButtonInput;
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
import com.terminalvelocitycabbage.game.client.inputcontrollers.CloseWindowController;
import com.terminalvelocitycabbage.game.client.inputcontrollers.DirectionalController;
import com.terminalvelocitycabbage.game.client.inputcontrollers.TestAxisController;
import com.terminalvelocitycabbage.game.client.renderer.SpinningSquareRenderer;
import com.terminalvelocitycabbage.game.client.renderer.node.OppositeSpinningSquareRenderNode;
import com.terminalvelocitycabbage.game.client.renderer.node.SpinningSquareRenderNode;

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

        //Build Render Graphs
        RenderGraph spinningSquareRenderGraph = RenderGraph.builder()
                .addNode(identifierOf("spinningSquare"), SpinningSquareRenderNode.class)
                .build();
        RenderGraph oppositeSpinningSquareRenderGraph = RenderGraph.builder()
                .addNode(identifierOf("oppositeSpinningSquare"), OppositeSpinningSquareRenderNode.class)
                .build();

        //Register renderers
        getRendererRegistry().register(identifierOf("game"), new Pair<>(SpinningSquareRenderer.class, spinningSquareRenderGraph));
        getRendererRegistry().register(identifierOf("game2"), new Pair<>(SpinningSquareRenderer.class, oppositeSpinningSquareRenderGraph));

        //Register Input Stuff
        //Register Controls to listen to
        Control escapeControl = getInputHandler().registerControlListener(new KeyboardKeyControl(KeyboardInput.ESCAPE));
        Control startControl = getInputHandler().registerControlListener(new GamepadButtonControl(GamepadInput.Button.START));
        Control rightMouseButtonControl = getInputHandler().registerControlListener(new MouseButtonControl(MouseButtonInput.RIGHT_CLICK));
        Control rightTriggerControl = getInputHandler().registerControlListener(new GamepadAxisControl(GamepadInput.Axis.RIGHT_TRIGGER));
        Control leftJoystickForwardControl = getInputHandler().registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_JOYSTICK_UP));
        Control leftJoystickBackwardsControl = getInputHandler().registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_JOYSTICK_DOWN));
        Control leftJoystickLeftControl = getInputHandler().registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_JOYSTICK_LEFT));
        Control leftJoystickRightControl = getInputHandler().registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_JOYSTICK_RIGHT));
        Control gamepadAControl = getInputHandler().registerControlListener(new GamepadButtonControl(GamepadInput.Button.A));
        Control leftTriggerControl = getInputHandler().registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_TRIGGER));
        Control wControl = getInputHandler().registerControlListener(new KeyboardKeyControl(KeyboardInput.W));
        Control sControl = getInputHandler().registerControlListener(new KeyboardKeyControl(KeyboardInput.S));
        Control aControl = getInputHandler().registerControlListener(new KeyboardKeyControl(KeyboardInput.A));
        Control dControl = getInputHandler().registerControlListener(new KeyboardKeyControl(KeyboardInput.D));
        Control spaceControl = getInputHandler().registerControlListener(new KeyboardKeyControl(KeyboardInput.SPACE));
        Control lShiftControl = getInputHandler().registerControlListener(new KeyboardKeyControl(KeyboardInput.LEFT_SHIFT));
        //Register Controllers
        getInputHandler().registerController(identifierOf("closeWindowOnEscapeController"), new CloseWindowController(escapeControl, startControl, rightMouseButtonControl));
        getInputHandler().registerController(identifierOf("testFloatController"), new TestAxisController(leftJoystickLeftControl, rightTriggerControl));
        getInputHandler().registerController(identifierOf("forwardBackwardController"), new DirectionalController(
                new ControlGroup(leftJoystickForwardControl, wControl),
                new ControlGroup(leftJoystickBackwardsControl, sControl),
                new ControlGroup(leftJoystickLeftControl, aControl),
                new ControlGroup(leftJoystickRightControl, dControl),
                new ControlGroup(gamepadAControl, spaceControl),
                new ControlGroup(leftTriggerControl, lShiftControl)));

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
        //WindowProperties secondWindow = new WindowProperties(600, 400, "second window", identifierOf("game2"));
        long primaryWindow = getWindowManager().createNewWindow(defaultWindow);
        //getWindowManager().createNewWindow(secondWindow);
        getWindowManager().focusWindow(primaryWindow);

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