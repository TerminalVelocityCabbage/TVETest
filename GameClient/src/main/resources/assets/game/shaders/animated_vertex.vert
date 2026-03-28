#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec3 color;
layout (location=3) in vec2 textureCoord;
layout (location=4) in float boneIndex;

out vec3 outColor;
out vec2 outTextureCoord;
out vec3 outNormal;
out vec3 outWorldPos;

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
    vec4 worldPos = modelMatrix * boneTransform * vec4(position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPos;
    outColor = color;
    outTextureCoord = textureCoord;
    outNormal = normalize(modelMatrix * boneTransform * vec4(normal, 0.0)).xyz;
    outWorldPos = worldPos.xyz;
}
