#version 330 core
out vec4 FragColor;
#define NR_POINT_LIGHTS 4
uniform sampler2D ourTextures[NR_POINT_LIGHTS];
 in float ourTextureIndex;
in vec4 FragPosLightSpace;
struct Material
{
    //sampler2D diffuse;
    //vec3 diffuse;
    vec3 specular;
    float shininess;
};
struct Light
{
 vec3 position;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float constant;
    float linear;
    float quadratic;

};
uniform Material material;
uniform Light light;

in vec3 Normal;
in vec3 FragPos;
in vec3 TexCoord;
uniform vec3 viewPos;

struct PointLight {
    vec3 position;

    float constant;
    float linear;
    float quadratic;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};
#define NR_POINT_LIGHTS 4
uniform PointLight pointLights[NR_POINT_LIGHTS];

uniform sampler2D shadowMap;


float ShadowCalculation(vec4 fragPosLightSpace)
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
       float bias = 0.005;
       float shadow = currentDepth- bias >closestDepth?1.0 : 0.0;

       return shadow;
}


vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir,vec4 oricolor)
{
    vec3 lightDir = normalize(light.position - fragPos);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    // attenuation
    float distance    = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance +
  			     light.quadratic * (distance * distance));
    // combine results
    vec3 ambient  = light.ambient  * vec3(oricolor);
    vec3 diffuse  = light.diffuse  * diff * vec3(oricolor);
    vec3 specular = light.specular * spec * vec3(oricolor);
    ambient  *= attenuation;
    diffuse  *= attenuation;
    specular *= attenuation;



    return (ambient + diffuse + specular);
}

vec3 CalcDirLight(Light light, vec3 normal, vec3 viewDir,vec4 oricolor)
{
    vec3 lightDir = normalize(( light.position - FragPos));



    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    // combine results
    vec3 ambient  = light.ambient  * vec3(oricolor);
    vec3 diffuse  = light.diffuse  * diff * vec3(oricolor);
    vec3 specular = light.specular * spec * vec3(oricolor);
  float shadow = ShadowCalculation(FragPosLightSpace);




    return  (ambient + (1.0 - shadow) * (diffuse + specular)) ;
}

void main()
{


    vec4 oricolor ;

    if(ourTextureIndex<0){
         oricolor = vec4(TexCoord,ourTextureIndex*(-1));

   }else if(ourTextureIndex==0){ vec2 TexCoordReal=vec2(TexCoord.x,TexCoord.y);
               oricolor = texture(ourTextures[int(ourTextureIndex)], TexCoordReal);

   }
    
    if(oricolor.a<0.1)
    discard;

vec3 norm = normalize(Normal);//faxian guiyi


    vec3 viewDir = normalize(viewPos - FragPos);

    // phase 1: Directional lighting
    vec3 result = CalcDirLight(light, norm, viewDir,oricolor);





 for(int i = 0; i < NR_POINT_LIGHTS; i++)
        result += CalcPointLight(pointLights[i], norm, FragPos, viewDir,oricolor);





FragColor=vec4(result,oricolor.w);
}