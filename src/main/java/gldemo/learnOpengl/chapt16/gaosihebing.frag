#version 330 core
out vec4 FragColor;
in vec2 TexCoords;
 
uniform sampler2D ourTexture0;
uniform sampler2D ourTexture1;
//uniform float exposure;
void main()
{          float exposure=3;
    const float gamma = 2.2;
    vec3 hdrColor = texture(ourTexture0, TexCoords).rgb;
    vec3 bloomColor = texture(ourTexture1, TexCoords).rgb;
    hdrColor += bloomColor;
    vec3 result = vec3(1.0) - exp(-hdrColor * exposure);
    result = pow(result, vec3(1.0 / gamma));
    FragColor = vec4(result, 1.0f);
}