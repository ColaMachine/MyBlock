#version 330 core

//uniform sampler2D ourTexture0;
//uniform sampler2D ourTexture1;
//uniform sampler2D ourTexture2;
//uniform sampler2D ourTexture3;
//uniform sampler2D ourTexture4;
//uniform sampler2D ourTexture5;
//uniform sampler2D ourTexture6;
//uniform sampler2D ourTexture7;
//uniform sampler2D ourTexture8;
//flat in float ourTextureIndex;
struct Material
{
    sampler2D diffuse;
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
out vec4 color;
in vec3 Normal;
in vec3 FragPos;
in vec2 TexCoord;

uniform vec3 viewPos;
//uniform sampler2D ourTexture;
void main()
{


vec3 norm = normalize(Normal);//faxian guiyi
vec3 lightDir = normalize(light.position - FragPos);//guang de xian lu

float diff = max(dot(norm, lightDir), 0.0);// jisuan jiaodu dai lai de guangzhao



vec3 viewDir = normalize(viewPos - FragPos);
vec3 reflectDir = reflect(-lightDir, norm);
float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);



vec3 ambient = light.ambient * vec3(texture(material.diffuse, TexCoord));
vec3 diffuse = light.diffuse * diff * vec3(texture(material.diffuse, TexCoord));
vec3 specular = light.specular * spec ;//* vec3(texture(material.specular, TexCoord));

float distance = length(light.position - FragPos);
//float attenuation = 1.0f / (light.constant + light.linear*distance +light.quadratic*(distance*distance));
//ambient *= attenuation;
//diffuse *= attenuation;
//specular *= attenuation;

 //vec4(light.ambient*vec3(texture(material.diffuse,TexCoords)), 1.0f);
vec3 result = (ambient + diffuse + specular) * vec3(texture(material.diffuse,TexCoord));//
color = vec4(result, 1.0f);


}