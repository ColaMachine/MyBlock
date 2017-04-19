#version 330 core
out vec4 FragColor;
in vec2 TexCoords;
 //传入的亮光图片
uniform sampler2D ourTexture0;
 //是否水平
uniform bool horizontal;
 //从中心往四边的权重比
uniform float weight[5] = float[] (0.227027, 0.1945946, 0.1216216, 0.054054, 0.016216);

void main()
{             
    vec2 tex_offset = 1.0 / textureSize(ourTexture0, 0); // 获取一个纹理像素的大小
    vec3 result = texture(ourTexture0, TexCoords).rgb * weight[0]; // 当前fragment的贡献度
    if(horizontal)
    {
        for(int i = 1; i < 5; ++i)
        {
            result += texture(ourTexture0, TexCoords + vec2(tex_offset.x * i, 0.0)).rgb * weight[i];
            result += texture(ourTexture0, TexCoords - vec2(tex_offset.x * i, 0.0)).rgb * weight[i];
        }
    }
    else
    {
        for(int i = 1; i < 5; ++i)
        {
            result += texture(ourTexture0, TexCoords + vec2(0.0, tex_offset.y * i)).rgb * weight[i];
            result += texture(ourTexture0, TexCoords - vec2(0.0, tex_offset.y * i)).rgb * weight[i];
        }
    }
    FragColor = vec4(result, 1.0);
}