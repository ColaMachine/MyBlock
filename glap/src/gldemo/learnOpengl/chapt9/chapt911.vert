#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;
layout (location = 2) in vec2 texCoord;
out vec3 ourColor;
out vec2 TexCoord;
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;


void main()
{
vec4 newPosition = vec4(position, 1.0f);
 gl_Position =projection*view*model*newPosition;//(view*(model* ));


ourColor = color;
TexCoord = vec2(texCoord.x, texCoord.y);
}