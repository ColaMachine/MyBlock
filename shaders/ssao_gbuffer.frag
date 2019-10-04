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
uniform vec3 viewPos;
uniform vec3 lightPos;

//输出的视野空间的位置
layout (location = 0) out vec3  gPosition;
//输出的法线向量
layout (location = 1) out vec3 gNormal;
//物体颜色
layout (location = 2) out vec4 gAlbedoSpec;

    vec4 oricolor ;





void main()
{



    if(ourTextureIndex<0){
         oricolor = vec4(TexCoord,ourTextureIndex*(-1));

   }else {vec2 TexCoordReal=vec2(TexCoord.x,TexCoord.y);
               oricolor = texture(ourTextures[int(ourTextureIndex)], TexCoordReal);

   }


if(oricolor.w<=0.1){
    discard;
}



 vec3 lightColor = vec3(1);
    // ambient
    vec3 ambient = 0.7 * oricolor.xyz;
    // diffuse
    vec3 lightDir = normalize(lightPos -FragPos);
    float diff = max(dot(lightDir, Normal), 0.0);
    vec3 diffuse = diff * lightColor;
    // specular
    vec3 viewDir = normalize(viewPos -FragPos);
    vec3 reflectDir = reflect(-lightDir, Normal);
    float spec = 0.0;
    vec3 halfwayDir = normalize(lightDir + viewDir);
    spec = pow(max(dot(Normal, halfwayDir), 0.0), 64.0);
    vec3 specular = spec * lightColor;
    // calculate shadow  现在的

    //2017年9月21日17:09:56  vec3 lighting = (ambient + (1.0 - shadow) * (diffuse + specular )) * oricolor.xyz;  把镜反去掉了 很难看
    vec3 lighting = (ambient +  diffuse ) * oricolor.xyz;



 gPosition = FragPos;
 gAlbedoSpec= vec4(lighting,1) ;
  gNormal = normalize(Normal);



    // 储存线性深度到gPositionDepth的alpha分量
 //   gPosition.a = LinearizeDepth(gl_FragCoord.z);
//.rgb = vec3(0.95);
//gAlbedoSpec = oricolor;
//gAlbedoSpec.rgb=oricolor.rgb;
//gAlbedoSpec.r=1;
// gPosition = FragPos;

}