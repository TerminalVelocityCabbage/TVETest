#version 330 core

layout (location = 0) out vec4 gPosition;
layout (location = 1) out vec4 gNormal;
layout (location = 2) out vec4 gAlbedo;

in vec3 FragPos;
in vec2 TexCoords;
in vec3 Normal;

uniform sampler2D textureSampler;

void main()
{
    // store the fragment position vector in the first gbuffer texture
    gPosition = vec4(FragPos, 1.0);
    // also store the per-fragment normals into the gbuffer
    gNormal = vec4(normalize(Normal), 1.0);
    // and the diffuse per-fragment color
    gAlbedo.rgb = texture(textureSampler, TexCoords).rgb;
    gAlbedo.a = 1.0;
}
