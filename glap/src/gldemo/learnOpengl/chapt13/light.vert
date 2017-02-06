#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec3 inColor;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out vec3 outColor;
void main()
{
vec4 newPosition = vec4(position, 1.0f);
 gl_Position =projection*view*model*newPosition;//(view*(model* ));
outColor=inColor;

}