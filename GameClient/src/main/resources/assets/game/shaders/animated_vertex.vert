#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 color;
layout (location=2) in vec2 textureCoord;
layout (location=3) in float boneIndex;

out vec3 outColor;
out vec2 outTextureCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 boneMatrices[128];

void main()
{
    int index = int(boneIndex);
    mat4 boneTransform = mat4(1.0);
    if (index >= 0 && index < 128) {
        boneTransform = boneMatrices[index];
    }
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * boneTransform * vec4(position, 1.0);
    outColor = color;
    outTextureCoord = textureCoord;
}
