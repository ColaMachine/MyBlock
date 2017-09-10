#version 330 core
layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gAlbedoSpec;
 
in vec2 TexCoords;
in vec3 FragPos;
in vec3 Normal;
 
uniform sampler2D texture_diffuse1;
uniform sampler2D texture_specular1;
 
void main()
{    
    // 在第一个gbuffer纹理中储存fragment位置向量
    gPosition = FragPos;
    // 同时在gbuffer储存前期的fragment法线
    gNormal = normalize(Normal);
    // 还有前期的fragment的diffuse颜色
    gAlbedoSpec.rgb = texture(texture_diffuse1, TexCoords).rgb;
    // 在gAlbedoSpec的alpha元素中储存specular强度
    gAlbedoSpec.a = texture(texture_specular1, TexCoords).r;
}