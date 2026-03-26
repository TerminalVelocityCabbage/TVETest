#version 330

#include "terminalvelocityengine:shader:directional_lights";

in vec3 outColor;
in vec2 outTextureCoord;
in vec3 outNormal;
in vec3 outWorldPos;
out vec4 fragColor;

uniform sampler2D textureSampler;
uniform DirectionalLight directionalLight;

void main()
{
    vec4 texColor = texture(textureSampler, outTextureCoord);
    if (texColor.a == 0) discard;

    vec3 normal = normalize(outNormal);
    float diff = max(dot(normal, -directionalLight.direction), 0.0);
    vec3 diffuse = diff * directionalLight.color.rgb * directionalLight.intensity;

    // Ambient part
    vec3 ambient = 0.5 * directionalLight.color.rgb;

    fragColor = vec4(ambient + diffuse, 1.0) * texColor;
}