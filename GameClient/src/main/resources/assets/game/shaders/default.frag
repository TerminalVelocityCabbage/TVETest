#version 330

in vec3 outColor;
in vec2 outTextureCoord;
out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
    fragColor = texture(textureSampler, outTextureCoord);
    //fragColor = vec4(outTextureCoord.r, outTextureCoord.g, 0, 1);
    //fragColor = vec4(outColor, 1);
}