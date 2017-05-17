#version 330 core
out vec4 FragColor;
//uniform sampler2D shadowMap;
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
//in vec4 FragPosLightSpace;

uniform vec3 viewPos;
//uniform sampler2D ourTexture;


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
   // color.w=1;
    vec3 textureColor = oricolor.rgb;
vec3 norm = normalize(Normal);//faxian guiyi
vec3 lightDir = normalize(light.position - FragPos);//guang de xian lu

float diff = max(dot(norm, lightDir), 0.0);// jisuan jiaodu dai lai de guangzhao

//float shadow = ShadowCalculation(FragPosLightSpace);

vec3 viewDir = normalize(viewPos - FragPos);
vec3 reflectDir = reflect(-lightDir, norm);
float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);

vec3 ambient = light.ambient * textureColor;
vec3 diffuse = light.diffuse * diff * textureColor;
vec3 specular = light.specular * spec ;//* vec3(texture(material.specular, TexCoord));

float distance = length(light.position - FragPos);
float attenuation = 5.0f / (light.constant + light.linear*distance +light.quadratic*(distance*distance));
ambient *= attenuation;
diffuse *= attenuation;
specular *= attenuation;

 FragColor=vec4(light.ambient*vec3(texture(material.diffuse,TexCoords)), 1.0f);
//vec3 result = (ambient + diffuse + specular) * textureColor;//
//vec3 color =result  ;
//color =oricolor;//vec4(result,oricolor.w)  ;    // oricolor;//vec4(textureColor, 0.5f);
//vec3 lighting =(ambient + (1.0 - shadow) * (diffuse + specular)) * textureColor;
//vec3 lighting =(ambient + diffuse + specular) * textureColor;
//改用固定数值试试看
//vec3 lighting =(0.7 + (1.0 - shadow) * (0.3 + 0.3)) * textureColor;

//if(shadow>0){
//lighting=vec3(0.1,0.1,0.1);
//}
   // FragColor = vec4(lighting, 1.0f);
// 直接显示原来的颜色
//color =oricolor;

}