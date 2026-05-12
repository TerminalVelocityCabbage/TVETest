package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.tvevents.EventBus;
import com.terminalvelocitycabbage.game.client.registry.*;
import com.terminalvelocitycabbage.game.common.GameCommon;
import com.terminalvelocitycabbage.templates.events.*;

public class GameClient extends ClientBase {

    public static final String ID = GameCommon.ID;

    public GameClient() {
        super(ID, 50);
    }

    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.start();
    }

    @Override
    public void registerEventListeners(EventBus bus) {
        bus.subscribe(ResourceCategoryRegistrationEvent.class).handle(GameResources::registerResourceCategories);
        bus.subscribe(ResourceSourceRegistrationEvent.class).handle(event -> GameResources.registerResourceSources(event, this));
        bus.subscribe(InputHandlerRegistrationEvent.class).handle(GameInput::init);
        bus.subscribe(EntityComponentRegistrationEvent.class).handle(GameEntities::registerComponents);
        bus.subscribe(EntitySystemRegistrationEvent.class).handle(GameEntities::createSystems);
        bus.subscribe(EntityTemplateRegistrationEvent.class).handle(GameEntities::createEntityTemplates);
        bus.subscribe(RoutineRegistrationEvent.class).handle(GameRoutines::init);
        bus.subscribe(RendererRegistrationEvent.class).handle(GameRenderers::init);
        bus.subscribe(FontRegistrationEvent.class).handle(GameFonts::registerFonts);
        bus.subscribe(SceneRegistrationEvent.class).handle(GameScenes::init);
        bus.subscribe(LocalizedTextKeyRegistrationEvent.class).handle(GameLocalizedTexts::registerLocalizedTextKeys);
        bus.subscribe(MeshRegistrationEvent.class).handle(GameMeshes::init);
        bus.subscribe(AnimationConfigurationEvent.class).handle(GameModels::initAnimations);
        bus.subscribe(ModelConfigRegistrationEvent.class).handle(GameModels::init);
        bus.subscribe(GameStateRegistrationEvent.class).handle(GameStates::registerStates);
        bus.subscribe(ConfigureTexturesEvent.class).handle(GameTextures::cacheTextures);
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