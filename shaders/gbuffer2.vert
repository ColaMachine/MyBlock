#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec3 texCoord;
layout (location = 3) in float textureIndex;
out vec3 Normal;
out vec3    FragPos;
out  vec3 TexCoord;


uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out float ourTextureIndex;
void main()
{
   vec4 viewPos = view * model * vec4(position, 1.0);
     FragPos = viewPos.xyz;
     TexCoord = texCoord;

     mat3 normalMatrix = transpose(inverse(mat3(view * model)));
     Normal = normalMatrix * normal ;
    ourTextureIndex= textureIndex;
     gl_Position = projection * viewPos;

}

