#version 330 core
out vec4 FragColor;
uniform sampler2D shadowMap;
uniform sampler2D ourTexture0;
uniform sampler2D ourTexture1;
uniform sampler2D ourTexture2;
uniform sampler2D ourTexture3;
uniform sampler2D ourTexture4;
uniform sampler2D ourTexture5;
uniform sampler2D ourTexture6;
uniform sampler2D ourTexture7;
uniform sampler2D ourTexture8;
 in float ourTextureIndex;

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
//out vec4 color;
in vec3 Normal;
in vec3 FragPos;
in vec3 TexCoord;
in vec4 FragPosLightSpace;

uniform vec3 viewPos;
//uniform sampler2D ourTexture;

float ShadowCalculation(vec4 fragPosLightSpace)
{
   // perform perspective divide
       vec3 projCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;//
       // Transform to [0,1] range
       projCoords = projCoords * 0.5 + 0.5;
       // Get closest depth value from light's perspective (using [0,1] range fragPosLight as coords)
       float closestDepth = texture(shadowMap, projCoords.xy).r;
       // Get depth of current fragment from light's perspective
       float currentDepth = projCoords.z;
       // Check whether current frag pos is in shadow
       //设置阴影偏移
       float bias = 0.005;
       float shadow = currentDepth- bias >closestDepth?1.0 : 0.0;//

       return shadow;
}


vec4 calcPointLight(vec3 normal,vec4 color)
{
    vec3 lightDirection = FragPos - lightPosition;
    float distanceToPoint = length(lightDirection);

    if(distanceToPoint > 40)
        return vec4(0,0,0,0);

    lightDirection = normalize(lightDirection);

    float diffuseFactor = dot(Normal, -lightDirection);

    vec4 color = vec4(theColor, 1.0) * diffuseFactor *light.ambient;

    float attenuation =Light.constant +
                         Light.linear * distanceToPoint +
                         0.0001 * distanceToPoint * distanceToPoint +
                         0.0001;

    return color / attenuation;
}


void main()
{




    vec4 oricolor ;
    vec2 TexCoordReal=vec2(TexCoord.x,TexCoord.y);
    if(ourTextureIndex==-1){
         oricolor = vec4(TexCoord,1);

    }else if(ourTextureIndex==0){
            oricolor = texture(ourTexture0, TexCoordReal);

    }else if(ourTextureIndex==1){
        oricolor = texture(ourTexture1, TexCoordReal);

    }else if(ourTextureIndex==2){
        oricolor = texture(ourTexture2, TexCoordReal);
    }else if(ourTextureIndex==3){
        oricolor = texture(ourTexture3, TexCoordReal);
    }else if(ourTextureIndex==4){
        oricolor = texture(ourTexture4, TexCoordReal);
    }else if(ourTextureIndex==5){
        oricolor = texture(ourTexture5, TexCoordReal);
    }else if(ourTextureIndex==6){
        oricolor = texture(ourTexture6, TexCoordReal);
    }else if(ourTextureIndex==7){
        oricolor = texture(ourTexture7, TexCoordReal);
    }else if(ourTextureIndex==8){
        oricolor = texture(ourTexture8, TexCoordReal);
    }else{
       oricolor = vec4(0.5,0.5,0.5,1);
    }

    if(oricolor.a<0.1)
    discard;

    vec4 someColor = calcPointLight(normalize(Normal),oricolor);


}