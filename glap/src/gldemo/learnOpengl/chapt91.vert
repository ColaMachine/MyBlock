#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;
layout (location = 2) in vec2 texCoord;
out vec3 ourColor;
out vec2 TexCoord;
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;


void main()
{
 gl_Position =  ((view*(model*vec4(position, 1.0f)))*projection);

  gl_Position.x=gl_Position.x/position.z;
   gl_Position.y=gl_Position.y/position.z;
   gl_Position.z=-1*gl_Position.z/position.z;
     gl_Position.w=gl_Position.w/position.z;
  //  if(gl_Position.w<0 || ){
    gl_Position.w=2;
  //  }

if(gl_Position.x>=0 ){
gl_Position.x=0.5*2;
}
if(gl_Position.x<0 ){
gl_Position.x=-0.5*2;
}
if(gl_Position.y<0 ){
gl_Position.y=-0.5*2;
}
if(gl_Position.y>=0 ){
gl_Position.y=0.5*2;
}
if(gl_Position.z<0 ){
gl_Position.z=-0.5*2;
}
if(gl_Position.z>=0 ){
gl_Position.z=-0.5*2;
}
ourColor = color;
TexCoord = vec2(texCoord.x, texCoord.y);
}