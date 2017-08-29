#version 330 core
struct Material
{
    sampler2D diffuse;
    //vec3 diffuse;
    vec3 specular;
    float shininess;
};
struct Light
{

vec3 ambient;
   vec3 diffuse;
    vec3 specular;
    float shininess;

};
uniform Material material;
uniform Light light;
out vec4 color;
in vec3 Normal;
in vec3 FragPos;
in vec2 TexCoord;
uniform vec3 objectColor;
uniform vec3 lightColor;
uniform vec3 lightPos;
uniform vec3 viewPos;
//uniform sampler2D ourTexture;
void main()
{
float ambientStrength = 0.1f;//huan jing yin zi
//vec3 ambient = ambientStrength * lightColor;// jisuan chu huanjing guang
//vec3 ambient=lightColor*material.ambient;
vec3 ambient = light.ambient * vec3(texture(material.diffuse, TexCoord));
//color = vec4(lightColor*objectColor, 1.0f);
vec3 norm = normalize(Normal);//faxian guiyi
vec3 lightDir = normalize(lightPos - FragPos);//guang de xian lu

float diff = max(dot(norm, lightDir), 0.0);// jisuan jiaodu dai lai de guangzhao
//vec3 diffuse = diff * lightColor;// jisuan guangzhao qiangdu
//vec3 diffuse=lightColor*(diff*material.diffuse);
vec3 diffuse = light.diffuse * diff * vec3(texture(material.diffuse, TexCoord));
float   specularStrength = 0.5f;
vec3 viewDir = normalize(viewPos - FragPos);
vec3 reflectDir = reflect(-lightDir, norm);
float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
vec3 specular = specularStrength * spec * lightColor;
//material.specular=vec3( 0.5f, 0.5f, 0.5f);

//vec3 specular = spec * ve c3( 0.5f, 0.5f, 0.5f);

vec3 result = (ambient + diffuse + specular) * objectColor;
//vec3 result = (ambient + diffuse) * objectColor;
color = vec4(result, 1.0f);
//color = texture(ourTexture, TexCoord)*color;


}