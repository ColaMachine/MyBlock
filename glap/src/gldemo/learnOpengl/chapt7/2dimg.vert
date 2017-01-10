#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in int textureIndex;
out vec2 TexCoord;
flat out float ourTextureIndex;
void main()
{
gl_Position = vec4(position, 1.0f);
ourTextureIndex=textureIndex;
TexCoord = texCoord;
}