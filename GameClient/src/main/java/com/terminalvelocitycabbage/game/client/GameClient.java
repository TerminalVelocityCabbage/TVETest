package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceSource;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.filesystem.sources.MainSource;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.translation.Language;
import com.terminalvelocitycabbage.engine.translation.Localizer;
import com.terminalvelocitycabbage.game.client.registry.*;
import com.terminalvelocitycabbage.game.common.GameCommon;

public class GameClient extends ClientBase {

    public static final String ID = GameCommon.ID;
    public static Identifier CLIENT_RESOURCE_SOURCE;
    public final Localizer localizer = new Localizer();

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
        clientSource.registerDefaultSourceRoot(ResourceType.LOCALIZATION);
        //register this source to the filesystem
        CLIENT_RESOURCE_SOURCE = getFileSystem().registerResourceSource(sourceIdentifier, clientSource).getIdentifier();

        //Register things
        GameConfigs.init(this);
        GameShaders.init(this);
        GameTextures.init(this);
        GameInput.init(this);
        GameEntities.init(this);
        GameRoutines.init(this);
        GameRenderers.init(this);
        GameScenes.init(this);
        GameLocalizedTexts.init();
    }

    @Override
    public void init() {
        super.init();

        localizer.init();

        //Create windows based on some initial properties
        WindowProperties defaultWindow = new WindowProperties(600, 400, "initial window", GameScenes.DEFAULT_SCENE);
        //WindowProperties secondWindow = new WindowProperties(600, 400, "second window", identifierOf("game2"));
        long primaryWindow = getWindowManager().createNewWindow(defaultWindow);
        //getWindowManager().createNewWindow(secondWindow);
        getWindowManager().focusWindow(primaryWindow);

        modInit();

        Log.info(localizer.localize(GameLocalizedTexts.HELLO));
        Log.info(localizer.localize(GameLocalizedTexts.GOODBYE));
        Log.info(localizer.localize(GameLocalizedTexts.ANOTHER_TRANSLATION));
        Log.info(localizer.localize(GameLocalizedTexts.TEST, "one", "two"));
        localizer.changeLanguage(Language.SPANISH_SPAIN);
        Log.info(localizer.localize(GameLocalizedTexts.HELLO));
        Log.info(localizer.localize(GameLocalizedTexts.GOODBYE));
        Log.info(localizer.localize(GameLocalizedTexts.ANOTHER_TRANSLATION));
        Log.info(localizer.localize(GameLocalizedTexts.TEST, "uno", "dos"));

        connect("127.0.0.1", 4132);

        //Test stuff
        GameTests.run(this);
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

}