#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in float textureIndex;

layout (location = 3) in vec4 inColor;
out vec2 TexCoord;

out vec4 ourcolor;
 out float ourTextureIndex;
void main()
{
gl_Position = vec4(position, 1.0f);
gl_Position.z=gl_Position.z/0.9-0.9;

ourTextureIndex=textureIndex;
TexCoord = texCoord;
ourcolor=inColor;
}