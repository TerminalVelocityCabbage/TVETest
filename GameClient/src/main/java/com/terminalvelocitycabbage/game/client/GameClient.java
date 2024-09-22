package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceSource;
import com.terminalvelocitycabbage.engine.filesystem.sources.MainSource;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.translation.Localizer;
import com.terminalvelocitycabbage.game.client.registry.*;
import com.terminalvelocitycabbage.game.common.GameCommon;
import com.terminalvelocitycabbage.game.common.events.ModLocalizedTextRegistryEvent;
import com.terminalvelocitycabbage.templates.events.*;

public class GameClient extends ClientBase {

    public static final String ID = GameCommon.ID;
    public static Identifier CLIENT_RESOURCE_SOURCE;
    public final Localizer localizer = new Localizer();

    public GameClient() {
        super(ID, 50);
        //Register things
        getEventDispatcher().listenToEvent(ResourceCategoryRegistrationEvent.EVENT, event -> GameResourceCategories.registerResourceCategories((ResourceCategoryRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceSourceRegistrationEvent.EVENT, event -> registerResourceSources((ResourceSourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.DEFAULT_CONFIG), event -> GameConfigs.init((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.SHADER), event -> GameShaders.init((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.TEXTURE), event -> GameTextures.init((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.LOCALIZATION), event -> GameLocalizedTexts.init((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(InputHandlerRegistrationEvent.EVENT, event -> GameInput.init((InputHandlerRegistrationEvent) event));
        getEventDispatcher().listenToEvent(EntityComponentRegistrationEvent.EVENT, event -> GameEntities.registerComponents((EntityComponentRegistrationEvent) event));
        getEventDispatcher().listenToEvent(EntitySystemRegistrationEvent.EVENT, event -> GameEntities.createSystems((EntitySystemRegistrationEvent) event));
        getEventDispatcher().listenToEvent(EntityTemplateRegistrationEvent.EVENT, event -> GameEntities.createEntityTemplates((EntityTemplateRegistrationEvent) event));
        getEventDispatcher().listenToEvent(RoutineRegistrationEvent.EVENT, event -> GameRoutines.init((RoutineRegistrationEvent) event));
        getEventDispatcher().listenToEvent(RendererRegistrationEvent.EVENT, event -> GameRenderers.init((RendererRegistrationEvent) event));
        getEventDispatcher().listenToEvent(SceneRegistrationEvent.EVENT, event -> GameScenes.init((SceneRegistrationEvent) event));
    }

    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.start();
    }

    private void registerResourceSources(ResourceSourceRegistrationEvent event) {
        //Register and init filesystem things
        //Create resource sources for this client
        ResourceSource clientSource = new MainSource(ID, this);
        //Define roots for these resources
        clientSource.registerDefaultSources();
        //register this source
        CLIENT_RESOURCE_SOURCE = event.register(identifierOf("client_main_resource_source"), clientSource).getIdentifier();
    }

    @Override
    public void init() {
        super.init();

        getEventDispatcher().dispatchEvent(new ModLocalizedTextRegistryEvent(ModLocalizedTextRegistryEvent.EVENT, localizer));
        localizer.init();

        //Create windows based on some initial properties
        WindowProperties defaultWindow = new WindowProperties(600, 400, "initial window", GameScenes.DEFAULT_SCENE);
        //WindowProperties secondWindow = new WindowProperties(600, 400, "second window", identifierOf("game2"));
        long primaryWindow = getWindowManager().createNewWindow(defaultWindow);
        //getWindowManager().createNewWindow(secondWindow);
        getWindowManager().focusWindow(primaryWindow);

        modInit();

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

    public Localizer getLocalizer() {
        return localizer;
    }
}