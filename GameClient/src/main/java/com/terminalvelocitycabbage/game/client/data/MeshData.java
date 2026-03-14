package com.terminalvelocitycabbage.game.client.data;

import com.terminalvelocitycabbage.engine.client.renderer.elements.VertexAttribute;
import com.terminalvelocitycabbage.engine.client.renderer.elements.VertexFormat;

public class MeshData {

    public static final VertexFormat MESH_FORMAT = VertexFormat.builder()
            .addElement(VertexAttribute.XYZ_POSITION)
            .addElement(VertexAttribute.RGB_COLOR)
            .addElement(VertexAttribute.UV)
            .build();

    public static final VertexFormat GBUFFER_FORMAT = VertexFormat.builder()
            .addElement(VertexAttribute.XYZ_POSITION)
            .addElement(VertexAttribute.UV)
            .addElement(VertexAttribute.XYZ_NORMAL)
            .build();

    public static final VertexFormat DEBUG_QUAD_FORMAT = VertexFormat.builder()
            .addElement(VertexAttribute.XYZ_POSITION)
            .addElement(VertexAttribute.UV)
            .build();

}
