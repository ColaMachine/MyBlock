#version 330 core

#define NR_POINT_LIGHTS 4
// 纹理的数组
uniform sampler2D ourTextures[NR_POINT_LIGHTS];
//正在使用的纹理
 in float ourTextureIndex;
//视空间的normal
in vec3 Normal;
//视空间的点位置
in vec3 FragPos;
//纹理坐标
in vec3 TexCoord;



//输出的视野空间的位置
layout (location = 0) out vec3  gPosition;
//输出的法线向量
layout (location = 1) out vec3 gNormal;
//物体颜色
layout (location = 2) out vec4 gAlbedoSpec;

//阴影二人组
uniform sampler2D shadowMap;
in vec4 FragPosLightSpace;
in vec3   realFragPos;

//const float NEAR = 0.1; // 投影矩阵的近平面
//const float FAR = 50.0f; // 投影矩阵的远平面
//float LinearizeDepth(float depth)
//{
  //  float z = depth * 2.0 - 1.0; // 回到NDC
 //   return (2.0 * NEAR * FAR) / (FAR + NEAR - z * (FAR - NEAR));
//}
    vec4 oricolor ;



float ShadowCalculation(vec4 fragPosLightSpace )
{

       vec3 projCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;
       // Transform to [0,1] range
       projCoords = projCoords * 0.5 + 0.5;
       // Get closest depth value from light's perspective (using [0,1] range fragPosLight as coords)
       float closestDepth = texture(shadowMap, projCoords.xy).r;
       // Get depth of current fragment from light's perspective
       float currentDepth = projCoords.z;
       // Check whether current frag pos is in shadow
       //设置阴影偏移
      // float bias = 0.005;

      // float bias = max(0.05 * (1.0 - dot(normal, lightDir)), 0.005);

       float shadow = currentDepth >closestDepth?1.0 : 0.0;//- bias
 if(projCoords.z > 1.0)
        shadow = 0.0;
       return shadow;
}
float ShadowCalculation(vec4 fragPosLightSpace)
{
    // perform perspective divide
    vec3 projCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;
    // transform to [0,1] range
    projCoords = projCoords * 0.5 + 0.5;
    // get closest depth value from light's perspective (using [0,1] range fragPosLight as coords)
    float closestDepth = texture(shadowMap, projCoords.xy).r;
    // get depth of current fragment from light's perspective
    float currentDepth = projCoords.z;
    // calculate bias (based on depth map resolution and slope)
    vec3 normal = normalize(fs_in.Normal);
    vec3 lightDir = normalize(lightPos - fs_in.FragPos);
    float bias = max(0.05 * (1.0 - dot(normal, lightDir)), 0.005);
    // check whether current frag pos is in shadow
    // float shadow = currentDepth - bias > closestDepth  ? 1.0 : 0.0;
    // PCF
    float shadow = 0.0;
    vec2 texelSize = 1.0 / textureSize(shadowMap, 0);
    for(int x = -1; x <= 1; ++x)
    {
        for(int y = -1; y <= 1; ++y)
        {
            float pcfDepth = texture(shadowMap, projCoords.xy + vec2(x, y) * texelSize).r;
            shadow += currentDepth - bias > pcfDepth  ? 1.0 : 0.0;
        }
    }
    shadow /= 9.0;

    // keep the shadow at 0.0 when outside the far_plane region of the light's frustum.
    if(projCoords.z > 1.0)
        shadow = 0.0;

    return shadow;
}

void main()
{



    if(ourTextureIndex<0){
         oricolor = vec4(TexCoord,ourTextureIndex*(-1));

   }else {vec2 TexCoordReal=vec2(TexCoord.x,TexCoord.y);
               oricolor = texture(ourTextures[int(ourTextureIndex)], TexCoordReal);

   }

  float shadow = ShadowCalculation(FragPosLightSpace);
 gPosition = FragPos;
 gAlbedoSpec=(1-shadow)*oricolor;
  gNormal = normalize(Normal);



    // 储存线性深度到gPositionDepth的alpha分量
 //   gPosition.a = LinearizeDepth(gl_FragCoord.z);
//.rgb = vec3(0.95);
//gAlbedoSpec = oricolor;
//gAlbedoSpec.rgb=oricolor.rgb;
//gAlbedoSpec.r=1;
// gPosition = FragPos;








}