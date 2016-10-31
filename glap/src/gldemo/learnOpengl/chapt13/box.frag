#version 330 core
out vec4 color;
in vec3 Normal;
in vec3 FragPos;
uniform vec3 objectColor;
uniform vec3 lightColor;
uniform vec3 lightPos;
uniform vec3 viewPos;
void main()
{
float ambientStrength = 0.5f;//huan jing yin zi
vec3 ambient = ambientStrength * lightColor;// jisuan chu huanjing guang
//color = vec4(lightColor*objectColor, 1.0f);
vec3 norm = normalize(Normal);//faxian guiyi
vec3 lightDir = normalize(lightPos - FragPos);//guang de xian lu

float diff = max(dot(norm, lightDir), 0.0);// jisuan jiaodu dai lai de guangzhao
vec3 diffuse = diff * lightColor;// jisuan guangzhao qiangdu

float   specularStrength = 0.1f;
vec3 viewDir = normalize(viewPos - FragPos);
vec3 reflectDir = reflect(-lightDir, norm);
float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
vec3 specular = specularStrength * spec * lightColor;

vec3 result = (ambient + diffuse + specular) * objectColor;




//vec3 result = (ambient + diffuse) * objectColor;
color = vec4(result, 1.0f);


}