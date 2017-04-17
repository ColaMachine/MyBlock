#version 330 core
layout (location = 0) out vec4 FragColor;
layout (location = 1) out vec4 BrightColor;
[...]
void main()
{             
    [...] // 先计算常规光照, 输出
        FragColor = vec4(lighting, 1.0f);
        // 检查fragment是否大于一定阈限, 如果大于就当作亮色输出
        float brightness = dot(FragColor.rgb, vec3(0.2126, 0.7152, 0.0722));
        if(brightness > 1.0)
            BrightColor = vec4(FragColor.rgb, 1.0);
}