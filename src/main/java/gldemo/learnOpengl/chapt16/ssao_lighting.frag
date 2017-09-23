#version 330 core
out vec4 FragColor;

in vec2 TexCoords;

uniform sampler2D gPosition;
uniform sampler2D gNormal;
uniform sampler2D gAlbedo;
uniform sampler2D ssao;

struct Light {
    vec3 Position;
    vec3 Color;

    float Linear;
    float Quadratic;
};

const int NR_LIGHTS = 32;
uniform Light lights[NR_LIGHTS];

void main()
{
    // retrieve data from gbuffer
    //这个坐标是视野空间 的坐标
    vec3 FragPos = texture(gPosition, TexCoords).rgb;
    //这个坐标是视野空间的normal
    vec3 Normal = texture(gNormal, TexCoords).rgb;
    vec3 Diffuse = texture(gAlbedo, TexCoords).rgb;
     float Specular = texture(gAlbedo, TexCoords).a;
    float AmbientOcclusion = texture(ssao, TexCoords).r;

    // then calculate lighting as usual
    vec3 ambient = vec3(1 * Diffuse * AmbientOcclusion);

    vec3 lighting  = ambient;
    vec3 viewDir  = normalize(-FragPos); // viewpos is (0.0.0)
    for(int i = 0; i < NR_LIGHTS; ++i)
       {
       if(lights[i].Position.x!=0){
           // diffuse
             //vec3 lightDir = normalize(FragPos-lights[i].Position);
              // vec3 diffuse = max(dot(Normal, lightDir), 0.0) * Diffuse * lights[i].Color;
            vec3 diffuse = lights[i].Color;

           float distance = length(lights[i].Position - FragPos);
           float attenuation = 1.0 / (1.0 + lights[i].Linear * distance + lights[i].Quadratic * distance * distance);
           diffuse *= attenuation;

           lighting += diffuse ;
           }
       }
    FragColor = vec4(lighting, 1.0);
}

