package gldemo;

import glapp.GLApp;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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

    FloatBuffer vertices= FloatBuffer.allocate(2*NumYPoints);
    IntBuffer indices= IntBuffer.allocate(NumStrips*(NumPointsPerStrip+1));
	public void init(){
		int vbo,ebo;

		/*vbo=GL15.glGenBuffers(vertices);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 2*NumPoints*8, GL15.GL_STREAM_DRAW);
		GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY,vertices);*/
        if(false){
            System.out.println("unable to map vertex buffer");
            System.exit(0);
        }else{
            int i,j;
            float dx = (XEnd-XStart)/(NumXPoints-1);
            float dy= (YEnd-YStart)/(NumYPoints-1);
            int n=0;
            for(j=0;j<NumYPoints;++j){
                float y =YStart +j*dy;
                for(i=0;i<NumXPoints;++i){
                    float x=XStart +i*dx;
                    vertices.put(x);
                    vertices.put(y);
                }
            }

            vertices.flip();
            GL11.glVertexPointer(2, GL11.GL_FLOAT, vertices);
            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        }

        /*ebo=GL15.glGenBuffers(indices);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ebo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 2*NumPoints*8, GL15.GL_STREAM_DRAW);
        GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY,indices);*/
        if(indices==null){
            System.out.println("unable to map index buffer");
            System.exit(0);
        }else{
            int i,j;

            for(j=0;j<NumStrips;++j){//10*
                int bottomRow=j*NumYPoints;
                int topRow=bottomRow+NumYPoints;
                for(i=0;i<NumXPoints;++i){

                    indices.put(topRow+i);
                    indices.put(bottomRow+i);
                }
            }

           indices.flip();
           // GL15.glUnmapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER);

            GL31.glPrimitiveRestartIndex(RestartIndex);
            GL11.glEnable(GL31.GL_PRIMITIVE_RESTART);

        }

	}
    public void display(){
        int i,start;
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glColor3f(1, 1, 1);
        GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP,  indices);//NumStrips * (NumPointsPerStrip + 1), GL11.GL_UNSIGNED_SHORT,
    }
	

}
