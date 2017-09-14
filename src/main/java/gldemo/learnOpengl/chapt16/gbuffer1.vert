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
vec4 worldPos =  vec4(position, 1.0);
    FragPos = worldPos.xyz;
   TexCoord = texCoord;
Normal = normal;
  // mat3 normalMatrix = transpose(inverse(mat3(model)));
 //   Normal = normalMatrix * normal;

   gl_Position = projection * view * worldPos;


//gl_Position =projection*view*model*vec4(position, 1.0f);
//FragPos=vec3(model*vec4(position,1.0f));//真实的物体位置
//Normal=normal;
ourTextureIndex=textureIndex;
//TexCoord = texCoord;

}