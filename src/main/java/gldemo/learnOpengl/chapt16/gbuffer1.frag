#version 330 core

#define NR_POINT_LIGHTS 4
uniform sampler2D ourTextures[NR_POINT_LIGHTS];
 in float ourTextureIndex;

in vec3 Normal;
in vec3 FragPos;
in vec3 TexCoord;
layout (location = 0) out vec3  gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gAlbedoSpec;

//const float NEAR = 0.1; // 投影矩阵的近平面
//const float FAR = 50.0f; // 投影矩阵的远平面
//float LinearizeDepth(float depth)
//{
  //  float z = depth * 2.0 - 1.0; // 回到NDC
 //   return (2.0 * NEAR * FAR) / (FAR + NEAR - z * (FAR - NEAR));
//}
    vec4 oricolor ;

void main()
{



    if(ourTextureIndex<0){
         oricolor = vec4(TexCoord,ourTextureIndex*(-1));

   }else {vec2 TexCoordReal=vec2(TexCoord.x,TexCoord.y);
               oricolor = texture(ourTextures[int(ourTextureIndex)], TexCoordReal);

   }


 gPosition = FragPos;
 gAlbedoSpec=oricolor;
  gNormal = normalize(Normal);

    // 储存线性深度到gPositionDepth的alpha分量
 //   gPosition.a = LinearizeDepth(gl_FragCoord.z);
//.rgb = vec3(0.95);
//gAlbedoSpec = oricolor;
//gAlbedoSpec.rgb=oricolor.rgb;
//gAlbedoSpec.r=1;
// gPosition = FragPos;








}