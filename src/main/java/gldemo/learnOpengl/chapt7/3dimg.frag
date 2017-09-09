#version 330 core
#define NR_TEXTURES 8
uniform sampler2D ourTextures[NR_TEXTURES];

 in float ourTextureIndex;

out vec4 color;
in vec3 FragPos;
in vec3 TexCoord;
in vec3 Normal;
uniform vec3 viewPos;
//uniform sampler2D ourTexture;
void main()
{


    vec4 oricolor ;
    vec2 TexCoordReal=vec2(TexCoord.x,TexCoord.y);
   

    if(ourTextureIndex<0){
         oricolor = vec4(TexCoord,ourTextureIndex*(-1));

   }else{ 
               oricolor = texture(ourTextures[int(ourTextureIndex)], TexCoordReal);

   }
    //if(oricolor.a<0.1)
    //discard;
   // color.w=1;
vec3 norm = normalize(Normal);//faxian guiyi
vec3 lightDir = normalize(vec3(100,100,100)-FragPos);//guang de xian lu

float diff = max(dot(norm, lightDir), 0.0);
vec3 diffuse = diff * oricolor.xyz;
float ambientStrength = 0.1f;
    vec3 ambient = ambientStrength * oricolor.xyz;
vec3 result = ( ambient+diffuse) * oricolor.xyz;
color = vec4(result, 1.0f);




}