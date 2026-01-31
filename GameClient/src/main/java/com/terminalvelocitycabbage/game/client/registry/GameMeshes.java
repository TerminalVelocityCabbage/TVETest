package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.renderer.model.Mesh;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.events.MeshRegistrationEvent;
import com.terminalvelocitycabbage.templates.meshes.SquareDataMesh;

import static com.terminalvelocitycabbage.game.client.data.MeshData.MESH_FORMAT;

//TODO add registration of loaded meshes also instead of only these data meshes
public class GameMeshes {

    public static Identifier SQUARE_MESH;

    public static void init(MeshRegistrationEvent event) {
        SQUARE_MESH = event.registerMesh(GameClient.ID, "square", new Mesh(MESH_FORMAT, new SquareDataMesh()));
    }

}
