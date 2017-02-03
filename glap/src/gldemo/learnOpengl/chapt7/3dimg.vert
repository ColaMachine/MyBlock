#version 330 core
layout (location = 0) in vec3 position;

layout (location = 1) in vec2 texCoord;
layout (location = 2) in float textureIndex;

//layout (location = 3) in vec4 inColor;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out vec2 TexCoord;

//out vec4 ourcolor;
flat out float ourTextureIndex;
void main()
{
gl_Position =projection*view*model*vec4(position, 1.0f);
//gl_Position = vec4(position, 1.0f);
ourTextureIndex=textureIndex;
TexCoord = texCoord;
//ourcolor=inColor;
}