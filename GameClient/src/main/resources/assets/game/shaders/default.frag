#version 330

in vec3 outColor;
in vec2 outTextureCoord;
out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
    vec4 textureColor = texture(textureSampler, outTextureCoord);
    if (textureColor.a == 0) {
        discard;
    } else {
        fragColor = textureColor;
    }
    //fragColor = vec4(.8, .8, 1, 1);
    //fragColor = vec4(outTextureCoord.r, outTextureCoord.g, 0, 1);
    //fragColor = vec4(outColor, 1);
}