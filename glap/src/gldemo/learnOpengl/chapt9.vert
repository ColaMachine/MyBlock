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
 gl_Position = projection *view *model *vec4(position, 1.0f);//#projection *view *model *
 //gl_Position= vec4(position, 1.0f);
 vec4 myVec =projection[1];
 if(
projection[0].x >1 &&projection[0].x <1.1 &&  projection[0].y==0 && projection[0].z ==0 && projection[0].w==0
&&
 (myVec.y>1 && myVec.y<1.1 && myVec.x==0 && myVec.z==0 && myVec.w==0)&&
projection[2].x==0 && projection[2].y==0 && projection[2].z>-1.22 && projection[2].z<-1.1// && projection[2].w >-0.3 && projection[2].w<-0.2
 ){//||  myVec.y!=0||  myVec.z!=0||  myVec.w!=0
ourColor = vec3(0,0,0);
}else{
ourColor = vec3(1,1,1);
}
TexCoord = vec2(texCoord.x, texCoord.y);
}