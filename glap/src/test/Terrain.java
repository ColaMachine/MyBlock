package test;


public class Terrain {
	private String bmp_path="";
	public static void main(String args[]){/*
		//���·������άͼ��С������
		try {
			File _file = new File("1.jpg"); //�����ļ�
			BufferedImage bi=ImageIO.read(new File(""));
			Image src = javax.imageio.ImageIO.read(_file); //����Image����

			int width=src.getWidth(null); //�õ�Դͼ��

			int height=src.getHeight(null); //�õ�Դͼ��
			
			float[][] heights  =  new float[width][height];
			  byte lowest =(byte) 255;
		        byte hightest = 0;
		        
		        for(int i=0;i <width;i++  )    {
		            for(int j=0;j   <height;j++)         {
		                if (heights[i][j]  < lowest)
		                    lowest =heights[i][j] ;
		                if( heights[i][j]  > hightest)
		                    hightest = heights[i][j];
		                //����ÿ��������24λ����ָ����8λ������+3ָ����һ������
		               
		            }
		        }
		        
		        for(int i=0;i< heightMap.Width;i++)
		        {
		            for(int j=0; j< heightMap.Height; j++)
		            {
		                heights[i,j] = (float)(*p - lowest) / (float)(hightest - lowest) * (Max - Min) + Min;
		                p += 3;
		            }
		        }
		        
		        heightMap.UnlockBits(data);
		        //���㶥�㣬�������������
		        numVertices = heightMap.Width * heightMap.Height;
		        numIndices = 6 * (heightMap.Width - 1) * (heightMap.Height - 1);
		        numTriangles = 2 * (heightMap.Width - 1) * (heightMap.Height - 1);
		        //������������
		        Vector3[] verts = new Vector3[numVertices];
		        int[] index = new int[numIndices];
		        int x = 0;
		        int n = 0;
		        float dx = terrainSize / (float) heightMap.Height;
		        float dy = terrainSize / (float) heightMap.Width;
		        //��䶥������
		        for ( int i = 0; i < heightMap.Height; i ++)
		        {
		            for ( int j = 0; j < heightMap.Width; j ++)
		            {
		                verts[i*heightMap.Width+j] = new Vector3((float)j*dx -terrainSize/2f,heights[j,i],(float)i*dy -terrainSize/2f);
		            }
		        }
		        //�����������
		        for ( int i = 0; i < heightMap.Width-1; i ++)
		        {
		            for ( int j = 0; j < heightMap.Height-1; j ++)
		            {
		                x = i * heightMap.Width + j;
		                index[n++] = x;
		                index[n++] = x+1;
		                index[n++] = x+heightMap.Width+1;
		                index[n++] = x;
		                index[n++] = x+heightMap.Width;
		                index[n++] = x+heightMap.Width+1;
		            }
		        }
		        //���ö����Լ������
		        vb = new VertexBuffer(typeof(Vector3),numVertices,device,Usage.None,VertexFormats.Position,Pool.Default);
		        vb.SetData(verts,0,0);
		        ib = new IndexBuffer(typeof(int),numIndices,device,Usage.None,Pool.Default);
		        ib.SetData(index,0,0);
		} catch (IOException e) {
			// VIP Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	   public void DrawTerrain()
	    {
	        /*device.VertexFormat = VertexFormats.Position;
	        device.SetStreamSource(0,vb,0);
	        device.Indices = ib;
	        device.Transform.World = Matrix.Translation(0,0,0);
	        device.DrawIndexedPrimitives(PrimitiveType.TriangleList,0,0,numVertices,0,numTriangles);*/
	    }
}
