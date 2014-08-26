package gldemo;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL15;

import glapp.GLApp;

public class primrestart extends GLApp {
float XStart =-0.8f;
float XEnd=-0.8f;
float YStart=-0.8f;
float YEnd=0.8f;
int NumXPoints=1;
int NumYPoints=1;
int NumPoints=NumXPoints*NumYPoints;
int NumPointsPerStrip=2*NumYPoints;
	int NumStrips=NumYPoints-1;
	int RestartIndex=0xffff;
	public void init(){
		int vbo,ebo;
		ByteBuffer vertices;
		ByteBuffer indices;
		vbo=GL15.glGenBuffers(vertices);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 2*NumPoints*8, GL15.GL_STREAM_DRAW);
		GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY,vertices);
		
	}
	
	if(){
		System.out.println("unable tomap vertex buffer");
		System.exit(0);
	}else{
		
	}
}
