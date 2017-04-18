#version 330 core
layout (location = 0) in vec2 position;
layout (location = 2) in vec2 texCoord;
out  vec2 TexCoords;
void main()
{
gl_Position =vec4(position, 0,1.0f);
TexCoords= texCoord;
}