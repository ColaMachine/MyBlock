#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;
layout (location = 2) in vec2 texCoord;
out vec3 ourColor;
out vec2 TexCoord;
uniform mat4 projection;
//uniform mat4 view;
//uniform mat4 model;


void main()
{
 gl_Position =projection*vec4(position, 1.0f);//(view*(model* ));
// 0.5 0.5 0
 int a=0;
 if(position.x==0.5&&
    position.y==0.5  ){
   // (0.5, 0.5, -3.0, 1.0) ====> (1.2077868, 1.2077868, 1.004004, 3.0)=====>(0.4025956, 0.4025956, 0.334668, 1.0)

//(0.5, 0.3536941, -3.3534126, 1.0) ====> (1.2077868, 0.8543742, 1.3581243, 3.3534126)=====>(0.36016646, 0.25477752, 0.40499765, 1.0)

if(position.z>-3.1 &&  position.z<-2.99  ){
    a=1;
}

if(gl_Position.x>0.5 && gl_Position.x<0.51 ){
//a=1;
}
if(gl_Position.y>0.5 && gl_Position.y<0.51 ){
//a=1;
}
if(gl_Position.z>=-1 &&gl_Position.z<=-0.95 ){
//a=1;
}

if(projection[0].x<2.5 && projection[0].x>2.4){
//a=1;
}

if(projection[1].y<2.5 && projection[1].y>2.4){
//a=1;
}
if(projection[2].z<-0.99 && projection[2].z>-1.1){
//a=1;
}
if(projection[3].z<-0.99 && projection[3].z>-1.1){
//a=1;
}
if(projection[2].w<-1.99 && projection[2].w>-2.1){
//a=1;
}
 }
 if(position.x==0.5&&
     position.y==-0.5  ){
a=1;


  }
  if(position.x==-0.5&&
      position.y==-0.5  ){

a=1;

   }
   if(position.x==-0.5&&
       position.y==0.5  ){
a=1;


    }
 //0.5 -0.5 0
 //-0.5 -0.5 0

 //-0.5 0.5 0
 //(1.2077868, 0.8543742, 1.3581243, 3.3534126)=
//if(position.x>1.2 &&position.x<1.3 &&
//position.y>0.85 &&position.y<0.86 &&
//position.z>1.2 &&position.z<1.3

 // ){
  //  gl_Position.x=0.5;
  //  gl_Position.y=0.5;
  //  gl_Position.z=0.5;
 //   gl_Position.w=1;
//}else{
if(a==1){
 gl_Position.x=-1*gl_Position.x/position.z;
   gl_Position.y=-1*gl_Position.y/position.z;
   gl_Position.z=-1*gl_Position.z/position.z;
     gl_Position.w=-1*gl_Position.w/position.z;
   // if(gl_Position.w<1011110 || gl_Position.w>111100  ){
   // gl_Position.w=1;
   // }
gl_Position.w=1;
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
}


ourColor = color;
TexCoord = vec2(texCoord.x, texCoord.y);
}