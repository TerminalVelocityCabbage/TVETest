#version 330

in vec3 outColor;
in vec2 outTextureCoord;
out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
    vec4 color = texture(textureSampler, outTextureCoord);
    if (color.a == 0) discard;
    fragColor = color;
    //fragColor *= vec4(.8, .8, 1, 1);
    //fragColor = vec4(outTextureCoord.r, outTextureCoord.g, 0, 1);
    //fragColor = vec4(outColor, 1);
}