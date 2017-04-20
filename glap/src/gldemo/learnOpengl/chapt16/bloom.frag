#version 330 core
layout (location = 0) out vec4 FragColor;
layout (location = 1) out vec4 BrightColor;
in vec2 TexCoords;
uniform sampler2D ourTexture0;
void main()
{
    vec3 lighting = texture(ourTexture0, TexCoords).rgb;
    float brightness = dot(lighting, vec3(0.2126, 0.7152, 0.0722));
    if(brightness > 1.0){
        BrightColor = vec4(lighting, 1.0);
    }
}