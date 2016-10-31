#version 330 core

layout (location=0) in vec3 position;
layout (location = 1) in vec3 color; // 颜色变量的属性position为 1
out vec3 vertexColor;
void main(){
gl_Position = vec4(position,1.0);
vertexColor = color;
}