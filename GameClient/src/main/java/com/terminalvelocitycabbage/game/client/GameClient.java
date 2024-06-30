package com.terminalvelocitycabbage.game.client;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.ConfigWriter;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.elements.VertexAttribute;
import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.client.renderer.model.Vertex;
import com.terminalvelocitycabbage.engine.client.window.WindowProperties;
import com.terminalvelocitycabbage.engine.config.TVConfig;
import com.terminalvelocitycabbage.engine.debug.Log;
import com.terminalvelocitycabbage.engine.filesystem.resources.Resource;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceSource;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceType;
import com.terminalvelocitycabbage.engine.filesystem.sources.MainSource;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.ecs.MaterialComponent;
import com.terminalvelocitycabbage.game.client.ecs.MeshComponent;
import com.terminalvelocitycabbage.game.client.registry.*;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

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

        //Register things
        GameConfigs.init(this, sourceIdentifier);
        GameShaders.init(this, sourceIdentifier);
        GameTextures.init(this, sourceIdentifier);
        GameInput.init(this);

        //Setup this Client's Routine
        //routine = Routine.builder()
        //        .addNode(new Identifier(ID, "updateSchedules"), UpdateScheduleSystem.class)
        //        .build();

        getManager().registerComponent(MeshComponent.class);
        getManager().registerComponent(MaterialComponent.class);
        Vertex[] vertices = new Vertex[] {
                Vertex.builder()
                        .addAttribute(VertexAttribute.XYZ_POSITION, new float[]{-0.5f, 0.5f, 0.0f})
                        .addAttribute(VertexAttribute.RGB_COLOR, new float[]{0.5f, 0.0f, 0.0f})
                        .addAttribute(VertexAttribute.UV, new float[]{0, 1})
                        .build(),
                Vertex.builder()
                        .addAttribute(VertexAttribute.XYZ_POSITION, new float[]{-0.5f, -0.5f, 0.0f})
                        .addAttribute(VertexAttribute.RGB_COLOR, new float[]{0.0f, 0.5f, 0.0f})
                        .addAttribute(VertexAttribute.UV, new float[]{0, 0})
                        .build(),
                Vertex.builder()
                        .addAttribute(VertexAttribute.XYZ_POSITION, new float[]{0.5f, -0.5f, 0.0f})
                        .addAttribute(VertexAttribute.RGB_COLOR, new float[]{0.0f, 0.0f, 0.5f})
                        .addAttribute(VertexAttribute.UV, new float[]{1, 0})
                        .build(),
                Vertex.builder()
                        .addAttribute(VertexAttribute.XYZ_POSITION, new float[]{0.5f, 0.5f, 0.0f})
                        .addAttribute(VertexAttribute.RGB_COLOR, new float[]{0.0f, 0.5f, 0.5f})
                        .addAttribute(VertexAttribute.UV, new float[]{1, 1})
                        .build(),
        };
        int[] indices = new int[] {0, 1, 3, 3, 1, 2};

        //TODO replace with registries for textures
        //TODO replace with registry for meshes. mesh should include an identifier to a texture
        //TODO replace component Mesh field with an identifier for that mesh
        //TODO abstract our texture registry to a Texture cache
        //TODO texture cache should be configured to create a set of atlases or a texture array. For MC block textures a texture array is better, but for entities they're too different
        //TODO Texture cache should create Texture objects
        var entity = getManager().createEntity();
        entity.addComponent(MeshComponent.class).setMesh(new Mesh(MESH_FORMAT, vertices, indices));
        entity.addComponent(MaterialComponent.class).setTexture(identifierOf("smile.png"));
    }

    @Override
    public void init() {
        super.init();

        GameTextures.cacheTextures(this);
        GameRenderers.init(this, getRenderGraphRegistry());

        //Create windows based on some initial properties
        WindowProperties defaultWindow = new WindowProperties(600, 400, "initial window", identifierOf("scene"));
        //WindowProperties secondWindow = new WindowProperties(600, 400, "second window", identifierOf("game2"));
        long primaryWindow = getWindowManager().createNewWindow(defaultWindow);
        //getWindowManager().createNewWindow(secondWindow);
        getWindowManager().focusWindow(primaryWindow);

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
        Resource resource = getFileSystem().getResource(ResourceType.DEFAULT_CONFIG, new Identifier("game", "test.toml"));
        Log.info(resource.asString());

        //Test reading the string that is the mod config file (this is the part that will never work like this)
        Resource modResource = getFileSystem().getResource(ResourceType.DEFAULT_CONFIG, new Identifier("testmod", "testmod.toml"));
        Log.info(modResource.asString());
    }

    //TVE implements a few utilities on top of Night Config, we test those here
    public void testNightConfig() {
        //Test night config stuff
        FileConfig config = TVConfig.getOrCreateFileConfig(getFileSystem(), new Identifier("game", "test.toml"));
        config.load();
        ConfigFormat<CommentedConfig> tomlFormat = TomlFormat.instance();
        ConfigWriter tomlWriter = tomlFormat.createWriter();
        String toml = tomlWriter.writeToString(config);
        Log.info(toml);
    }
}