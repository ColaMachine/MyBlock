#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec3 texCoord;
layout (location = 3) in float textureIndex;
out vec3 Normal;
out vec3    FragPos;
out  vec3 TexCoord;
out float ourTextureIndex;

out vec3   realNormal;
out vec3   realFragPos;
out vec4 FragPosLightSpace;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform mat4 lightSpaceMatrix;

void main()
{
    vec4 viewPos = view * model * vec4(position, 1.0);
     FragPos = viewPos.xyz;
     TexCoord = texCoord;

     mat3 normalMatrix = transpose(inverse(mat3(view * model)));
    realFragPos=vec3(model*vec4(position,1.0f));//真实的物体位置;
     Normal = normalMatrix * normal ;
realNormal = normal;
     ourTextureIndex= textureIndex;
     gl_Position = projection * viewPos;
//转换成灯光视角中的位置
FragPosLightSpace = lightSpaceMatrix * vec4(position, 1.0);

}

