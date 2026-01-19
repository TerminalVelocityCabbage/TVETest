#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 textureCoord;
layout (location=2) in vec3 normal;
layout (location=3) in vec4 boneIndices;

out vec2 outTextureCoord;
out vec3 outNormal;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 boneTransforms[100];
uniform int useAnimation;

void main()
{
    vec4 pos = vec4(position, 1.0);
    if (useAnimation == 1) {
        int index = int(boneIndices.x);
        pos = boneTransforms[index] * pos;
    }
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * pos;
    outTextureCoord = textureCoord;
    outNormal = normal;
}