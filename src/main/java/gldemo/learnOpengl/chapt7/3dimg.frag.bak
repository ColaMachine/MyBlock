#version 330 core

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
       oricolor = vec4(1,1,1,1);
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