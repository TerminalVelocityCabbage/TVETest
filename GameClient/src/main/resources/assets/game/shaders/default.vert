#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 textureCoord;
layout (location=2) in vec3 normal;

out vec2 outTextureCoord;
out vec3 outNormal;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;

void main()
{
    vec4 pos = vec4(position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * pos;
    outTextureCoord = textureCoord;
    outNormal = normal;
}