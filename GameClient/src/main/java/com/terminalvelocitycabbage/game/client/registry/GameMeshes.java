package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.filesystem.resources.ResourceCategory;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.LoadBedrockModelsEvent;
import com.terminalvelocitycabbage.templates.events.MeshRegistrationEvent;
import com.terminalvelocitycabbage.templates.events.ResourceRegistrationEvent;
import com.terminalvelocitycabbage.templates.meshes.SquareDataMesh;

import java.util.HashMap;
import java.util.Map;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

//TODO add registration of loaded meshes also instead of only these data meshes
public class GameMeshes {

    public static Identifier SQUARE_MESH;
    public static Identifier MAIN_CHAR_MESH;

    private static final Map<Identifier, Mesh> MESH_CACHE = new HashMap<>();

    public static void registerResources(ResourceRegistrationEvent event) {
        MAIN_CHAR_MESH = event.registerResource(GameResources.CLIENT_RESOURCE_SOURCE, ResourceCategory.MODEL, "mainchar.geo.json").getIdentifier();
    }

    public static void cacheMeshes(LoadBedrockModelsEvent event) {
        event.cache(MESH_CACHE, MAIN_CHAR_MESH, MESH_FORMAT);
    }

    public static void init(MeshRegistrationEvent event) {

        var client = ClientBase.getInstance();

        SQUARE_MESH = event.register(client.identifierOf("square"), new Mesh(MESH_FORMAT, new SquareDataMesh())).getIdentifier();

        MESH_CACHE.forEach(event::register);
    }

}
