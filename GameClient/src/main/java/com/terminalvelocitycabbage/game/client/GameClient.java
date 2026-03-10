package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.event.EventDispatcher;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.game.client.registry.*;
import com.terminalvelocitycabbage.game.common.GameCommon;
import com.terminalvelocitycabbage.templates.events.*;

public class GameClient extends ClientBase {

    public static final String ID = GameCommon.ID;

    public GameClient() {
        super(ID, 50);
    }

    @Override
    public void registerEventListeners(EventDispatcher dispatcher) {
        dispatcher.listenToEvent(ResourceCategoryRegistrationEvent.EVENT, event -> GameResources.registerResourceCategories((ResourceCategoryRegistrationEvent) event));
        dispatcher.listenToEvent(ResourceSourceRegistrationEvent.EVENT, event -> GameResources.registerResourceSources((ResourceSourceRegistrationEvent) event, this));
        dispatcher.listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.DEFAULT_CONFIG), event -> GameConfigs.init((ResourceRegistrationEvent) event));
        dispatcher.listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.SHADER), event -> GameShaders.init((ResourceRegistrationEvent) event));
        dispatcher.listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.TEXTURE), event -> GameTextures.registerResources((ResourceRegistrationEvent) event));
        dispatcher.listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.FONT), event -> GameFonts.registerResources((ResourceRegistrationEvent) event));
        dispatcher.listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.LOCALIZATION), event -> GameLocalizedTexts.registerTranslationResources((ResourceRegistrationEvent) event));
        dispatcher.listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.ROUTINE), event -> GameRoutines.registerResources((ResourceRegistrationEvent) event));
        dispatcher.listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.ENTITY), event -> GameEntities.registerResources((ResourceRegistrationEvent) event));
        dispatcher.listenToEvent(InputHandlerRegistrationEvent.EVENT, event -> GameInput.init((InputHandlerRegistrationEvent) event));
        dispatcher.listenToEvent(EntityComponentRegistrationEvent.EVENT, event -> GameEntities.registerComponents((EntityComponentRegistrationEvent) event));
        dispatcher.listenToEvent(EntitySystemRegistrationEvent.EVENT, event -> GameEntities.createSystems((EntitySystemRegistrationEvent) event));
        dispatcher.listenToEvent(EntityTemplateRegistrationEvent.EVENT, event -> GameEntities.createEntityTemplates((EntityTemplateRegistrationEvent) event));
        dispatcher.listenToEvent(RoutineRegistrationEvent.EVENT, event -> GameRoutines.init((RoutineRegistrationEvent) event));
        dispatcher.listenToEvent(RendererRegistrationEvent.EVENT, event -> GameRenderers.init((RendererRegistrationEvent) event));
        dispatcher.listenToEvent(FontRegistrationEvent.EVENT, event -> GameFonts.registerFonts((FontRegistrationEvent) event));
        dispatcher.listenToEvent(SceneRegistrationEvent.EVENT, event -> GameScenes.init((SceneRegistrationEvent) event));
        dispatcher.listenToEvent(LocalizedTextKeyRegistrationEvent.EVENT, event -> GameLocalizedTexts.registerLocalizedTextKeys((LocalizedTextKeyRegistrationEvent) event));
        dispatcher.listenToEvent(MeshRegistrationEvent.EVENT, event -> GameMeshes.init((MeshRegistrationEvent) event));
        dispatcher.listenToEvent(ModelConfigRegistrationEvent.EVENT, event -> GameModels.init((ModelConfigRegistrationEvent) event));
        dispatcher.listenToEvent(GameStateRegistrationEvent.EVENT, event -> GameStates.registerStates((GameStateRegistrationEvent) event));
        dispatcher.listenToEvent(ConfigureTexturesEvent.EVENT, event -> GameTextures.cacheTextures((ConfigureTexturesEvent) event));
    }

    @Override
    public void init() {
        super.init();

        //Create windows based on some initial properties
        WindowProperties defaultWindow = new WindowProperties(600, 400, "initial window", GameScenes.DEFAULT_SCENE);
        //WindowProperties secondWindow = new WindowProperties(600, 400, "second window", identifierOf("game2"));
        long primaryWindow = getWindowManager().createNewWindow(defaultWindow);
        //getWindowManager().createNewWindow(secondWindow);
        getWindowManager().focusWindow(primaryWindow);

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