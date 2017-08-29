#version 330 core
out vec4 color;
in vec2 TexCoords;

uniform sampler2D ourTexture0;

 void main()
 {
    color = texture(ourTexture0, TexCoords);
 }