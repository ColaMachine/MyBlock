#version 330 core
layout (location = 0) in vec3 position;
//layout (location = 1) in vec3 normal;
layout (location = 2) in vec3 texCoord;
layout (location = 3) in float textureIndex;
uniform mat4 lightSpaceMatrix;
uniform mat4 model;
//uniform mat4 lightModel;

void main()
{
    //gl_Position = lightSpaceMatrix * model * vec4(position, 1.0f);这里的model 其实可以废弃了 因为基本是用不到的
    gl_Position = lightSpaceMatrix * model * vec4(position, 1.0f);
}