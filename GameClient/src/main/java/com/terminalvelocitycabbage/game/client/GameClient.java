package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.game.client.registry.*;
import com.terminalvelocitycabbage.game.common.GameCommon;
import com.terminalvelocitycabbage.templates.events.*;

public class GameClient extends ClientBase {

    public static final String ID = GameCommon.ID;

    public GameClient() {
        super(ID, 50);
        //Register things
        getEventDispatcher().listenToEvent(ResourceCategoryRegistrationEvent.EVENT, event -> GameResources.registerResourceCategories((ResourceCategoryRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceSourceRegistrationEvent.EVENT, event -> GameResources.registerResourceSources((ResourceSourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.DEFAULT_CONFIG), event -> GameConfigs.init((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.SHADER), event -> GameShaders.init((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.TEXTURE), event -> GameTextures.registerResources((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.MODEL), event -> GameMeshes.registerResources((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.ANIMATION), event -> GameAnimations.registerResources((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ResourceRegistrationEvent.getEventNameFromCategory(ResourceCategory.LOCALIZATION), event -> GameLocalizedTexts.registerTranslationResources((ResourceRegistrationEvent) event));
        getEventDispatcher().listenToEvent(InputHandlerRegistrationEvent.EVENT, event -> GameInput.init((InputHandlerRegistrationEvent) event));
        getEventDispatcher().listenToEvent(EntityComponentRegistrationEvent.EVENT, event -> GameEntities.registerComponents((EntityComponentRegistrationEvent) event));
        getEventDispatcher().listenToEvent(EntitySystemRegistrationEvent.EVENT, event -> GameEntities.createSystems((EntitySystemRegistrationEvent) event));
        getEventDispatcher().listenToEvent(EntityTemplateRegistrationEvent.EVENT, event -> GameEntities.createEntityTemplates((EntityTemplateRegistrationEvent) event));
        getEventDispatcher().listenToEvent(RoutineRegistrationEvent.EVENT, event -> GameRoutines.init((RoutineRegistrationEvent) event));
        getEventDispatcher().listenToEvent(RendererRegistrationEvent.EVENT, event -> GameRenderers.init((RendererRegistrationEvent) event));
        getEventDispatcher().listenToEvent(SceneRegistrationEvent.EVENT, event -> GameScenes.init((SceneRegistrationEvent) event));
        getEventDispatcher().listenToEvent(LocalizedTextKeyRegistrationEvent.EVENT, event -> GameLocalizedTexts.registerLocalizedTextKeys((LocalizedTextKeyRegistrationEvent) event));
        getEventDispatcher().listenToEvent(MeshRegistrationEvent.EVENT, event -> GameMeshes.init((MeshRegistrationEvent) event));
        getEventDispatcher().listenToEvent(LoadBedrockModelsEvent.EVENT, event -> GameMeshes.cacheMeshes((LoadBedrockModelsEvent) event));
        getEventDispatcher().listenToEvent(LoadBedrockAnimationsEvent.EVENT, event -> GameAnimations.cacheAnimations((LoadBedrockAnimationsEvent) event));
        getEventDispatcher().listenToEvent(ModelConfigRegistrationEvent.EVENT, event -> GameModels.init((ModelConfigRegistrationEvent) event));
        getEventDispatcher().listenToEvent(GameStateRegistrationEvent.EVENT, event -> GameStates.registerStates((GameStateRegistrationEvent) event));
        getEventDispatcher().listenToEvent(ConfigureTexturesEvent.EVENT, event -> GameTextures.cacheTextures((ConfigureTexturesEvent) event));
    }

    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.start();
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