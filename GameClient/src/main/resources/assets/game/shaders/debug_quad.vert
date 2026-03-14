#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoord;

out vec2 TexCoords;

uniform mat4 modelMatrix;

void main()
{
    TexCoords = textureCoord;
    gl_Position = modelMatrix * vec4(position, 1.0);
}
