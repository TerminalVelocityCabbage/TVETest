#version 330 core
out vec4 FragColor;

in vec2 TexCoords;

uniform sampler2D screenTexture;
uniform bool isDepth;

void main()
{
    if (isDepth) {
        float depthValue = texture(screenTexture, TexCoords).r;
        // Make it visible (linearize if needed, but for simple test just show it)
        FragColor = vec4(vec3(depthValue), 1.0);
    } else {
        FragColor = texture(screenTexture, TexCoords);
    }
}
