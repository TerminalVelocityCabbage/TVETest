#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec3 color;
layout (location=3) in vec2 textureCoord;

out vec3 outColor;
out vec2 outTextureCoord;
out vec3 outNormal;
out vec3 outWorldPos;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;

void main()
{
    vec4 worldPos = modelMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPos;
    outColor = color;
    outTextureCoord = textureCoord;
    outNormal = normalize(modelMatrix * vec4(normal, 0.0)).xyz;
    outWorldPos = worldPos.xyz;
}