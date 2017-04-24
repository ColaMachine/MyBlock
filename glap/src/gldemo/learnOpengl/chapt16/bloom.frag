#version 330 core
layout (location = 0) out vec4 FragColor;
layout (location = 1) out vec4 BrightColor;
in vec2 TexCoords;
uniform sampler2D ourTexture0;
void main()
{
FragColor= texture(ourTexture0, TexCoords);
    vec3 lighting = FragColor.rgb;
    float brightness = dot(lighting, vec3(0.2126, 0.7152, 0.0722));
    //FragColor= lighting;
    if(brightness > 1){
        BrightColor = vec4(200,200,200, 1.0);
    }else{
     BrightColor = vec4(0,0,0, 1.0);
    }
}