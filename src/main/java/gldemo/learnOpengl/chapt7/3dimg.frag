#version 330 core

#define NR_POINT_LIGHTS 4
uniform sampler2D ourTextures[NR_POINT_LIGHTS];
 in float ourTextureIndex;

out vec4 color;

in vec3 TexCoord;
in vec3 Normal;
uniform vec3 viewPos;
//uniform sampler2D ourTexture;
void main()
{


    vec4 oricolor ;

     if(ourTextureIndex<0){
             oricolor = vec4(TexCoord,ourTextureIndex*(-1));

       }else if(ourTextureIndex==0){
         vec2 TexCoordReal=vec2(TexCoord.x,TexCoord.y);

                   oricolor = texture(ourTextures[int(ourTextureIndex)], TexCoordReal);

       }


    //if(oricolor.a<0.1)
    //discard;
   // color.w=1;


color = oricolor;


}