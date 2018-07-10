#version 120

uniform sampler2D tex0;
uniform sampler2D glitch;


void main() {
	vec2 tc = gl_TexCoord[0].st;
	vec4 color = texture2D(glitch,tc);

	if(texture2D(tex0,tc).a < 0.9)
	{
	    gl_FragColor = texture2D(tex0,tc);
	}


}