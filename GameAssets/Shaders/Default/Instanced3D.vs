#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix0;
uniform mat4 modelViewMatrix1;
uniform mat4 modelViewMatrix2;
uniform mat4 modelViewMatrix3;

void main()
{
    if(gl_InstanceID == 0)
    {
        gl_Position = projectionMatrix * modelViewMatrix0 * vec4((position), 1.0);

    }
    if(gl_InstanceID == 1)
    {
        gl_Position = projectionMatrix * modelViewMatrix1 * vec4((position), 1.0);

    }
    if(gl_InstanceID == 2)
    {
        gl_Position = projectionMatrix * modelViewMatrix2 * vec4((position), 1.0);

    }
    if(gl_InstanceID == 3)
    {
        gl_Position = projectionMatrix * modelViewMatrix3 * vec4((position), 1.0);

    }
    outTexCoord = texCoord;
}