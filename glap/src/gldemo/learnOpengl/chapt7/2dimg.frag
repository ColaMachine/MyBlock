#version 330 core
in vec2 TexCoord;
flat in float ourTextureIndex;
out vec4 color;

uniform sampler2D ourTexture0;
uniform sampler2D ourTexture1;
uniform sampler2D ourTexture2;
uniform sampler2D ourTexture3;
uniform sampler2D ourTexture4;
uniform sampler2D ourTexture5;
uniform sampler2D ourTexture6;
uniform sampler2D ourTexture7;
uniform sampler2D ourTexture8;
void main()
{
     if(ourTextureIndex==0){
            color = texture(ourTexture0, TexCoord);

    }else if(ourTextureIndex==1){
        color = texture(ourTexture1, TexCoord);

    }else if(ourTextureIndex==2){
        color = texture(ourTexture2, TexCoord);
    }else if(ourTextureIndex==3){
        color = texture(ourTexture3, TexCoord);
    }else if(ourTextureIndex==4){
        color = texture(ourTexture4, TexCoord);
    }else if(ourTextureIndex==5){
        color = texture(ourTexture5, TexCoord);
    }else if(ourTextureIndex==6){
        color = texture(ourTexture6, TexCoord);
    }else if(ourTextureIndex==7){
        color = texture(ourTexture7, TexCoord);
    }else if(ourTextureIndex==8){
        color = texture(ourTexture8, TexCoord);
    }else{
       color = vec4(0.5,0.5,0.5,1);
    }
}