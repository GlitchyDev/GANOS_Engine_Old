#version 120

uniform sampler2D main_Texture;
uniform sampler2D sub_Texture;
varying vec2 tex_coords;

void main() {
    if(texture2D(sub_Texture,tex_coords).r > 0.5)
    {

    }
    else
    {
        gl_FragColor = texture2D(main_Texture,tex_coords);
    }
}