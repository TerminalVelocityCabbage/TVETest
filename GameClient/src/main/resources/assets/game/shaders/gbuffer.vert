#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoord;
layout (location = 2) in vec3 normal;

out vec3 FragPos;
out vec2 TexCoords;
out vec3 Normal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main()
{
    vec4 worldPos = modelMatrix * vec4(position, 1.0);
    FragPos = worldPos.xyz;
    TexCoords = textureCoord;

    mat3 normalMatrix = transpose(inverse(mat3(modelMatrix)));
    Normal = normalMatrix * normal;

    gl_Position = projectionMatrix * viewMatrix * worldPos;
}
