#version 330

in vec2 outTexCoord;
in vec3 mvPos;
out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform sampler2D glitch_sampler;

void main()
{
    if(texture(glitch_sampler, outTexCoord).a != 0)
    {
        fragColor = texture(texture_sampler, outTexCoord);
    }
}