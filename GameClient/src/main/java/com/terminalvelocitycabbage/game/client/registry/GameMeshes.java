package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.ClientBase;
import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.templates.events.MeshRegistrationEvent;
import com.terminalvelocitycabbage.templates.meshes.SquareDataMesh;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

public class GameMeshes {

    public static Identifier SQUARE_MESH;

    public static void init(MeshRegistrationEvent event) {

        var client = ClientBase.getInstance();

        SQUARE_MESH = event.register(client.identifierOf("square"), new Mesh(MESH_FORMAT, new SquareDataMesh())).getIdentifier();
    }

}
