#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec2 textureCoord;
layout (location=3) in int boneIndex;

const int MAX_BONES = 128;

out vec3 outNormal;
out vec2 outTextureCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 boneTransformations[MAX_BONES];

void main()
{
    gl_Position = projectionMatrix * modelMatrix * boneTransformations[boneIndex] * vec4(position, 1.0);
    vec4 animatedNormal = boneTransformations[boneIndex] * vec4(normal, 1.0);
    outNormal = normalize(animatedNormal.xyz);
    outTextureCoord = textureCoord;
}