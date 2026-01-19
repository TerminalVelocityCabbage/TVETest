package com.terminalvelocitycabbage.game.client.data;

import com.terminalvelocitycabbage.engine.client.renderer.elements.VertexAttribute;
import com.terminalvelocitycabbage.engine.client.renderer.elements.VertexFormat;

public class MeshData {

    public static final VertexFormat MESH_FORMAT = VertexFormat.builder()
            .addElement(VertexAttribute.XYZ_POSITION)
            .addElement(VertexAttribute.UV)
            .addElement(VertexAttribute.XYZ_NORMAL)
            .addElement(VertexAttribute.BONE_INDICES)
            .build();

}
