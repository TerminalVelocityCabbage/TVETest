#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 color;
layout (location=2) in vec2 textureCoord;

out vec3 outColor;
out vec2 outTextureCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;

void main()
{
    gl_Position = projectionMatrix * modelMatrix * vec4(position, 1.0);
    outColor = color;
    outTextureCoord = textureCoord;
}