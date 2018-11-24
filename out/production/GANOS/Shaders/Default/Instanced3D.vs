#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
##layout (location=2) in vec4 modelViewMatrix;

out vec2 outTexCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;


void main()
{
    gl_Position = projectionMatrix * modelViewMatrix * vec4((position + gl_InstanceID), 1.0);
    outTexCoord = texCoord;
}