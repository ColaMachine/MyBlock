#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec3 texCoord;
layout (location = 3) in float textureIndex;

out  vec3 TexCoord;
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;
 out float ourTextureIndex;
void main()
{

gl_Position =projection*view*model*vec4(position, 1.0f);


ourTextureIndex=textureIndex;
TexCoord = texCoord;

}