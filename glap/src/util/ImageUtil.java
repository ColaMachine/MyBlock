package util;


import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import cola.machine.game.myblocks.Color;

public class ImageUtil {
	public static int[][] getGrayPicture(String filename) throws FileNotFoundException, IOException
	{	
		File file =new File(filename);
		BufferedImage originalImage=ImageIO.read(new FileInputStream(file));
		originalImage.getColorModel();
		System.out.println(originalImage.getColorModel());
		
		int green=0,red=0,blue=0,rgb;
		int imageWidth = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		int[][] heights=new int[imageWidth][imageHeight];
		for(int i = originalImage.getMinX();i < imageWidth ;i++)
			{
				for(int j = originalImage.getMinY();j < imageHeight ;j++)
				{
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
					Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ
				
					red = originalImage.getColorModel().getRed(data);
					blue = originalImage.getColorModel().getBlue(data);
					green = originalImage.getColorModel().getGreen(data);
					heights[i][j]=red;
				}
				
			}
		return heights;
		   
	}
	public static float[][] getGrayPicturef(String filename) throws FileNotFoundException, IOException
	{	
		File file =new File(filename);
		BufferedImage originalImage=ImageIO.read(new FileInputStream(file));
		originalImage.getColorModel();
		System.out.println(originalImage.getColorModel());
		
		int green=0,red=0,blue=0,rgb;
		int imageWidth = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		float[][] heights=new float[imageWidth][imageHeight];
		for(int i = originalImage.getMinX();i < imageWidth ;i++)
			{
				for(int j = originalImage.getMinY();j < imageHeight ;j++)
				{
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
					Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ
				
					red = originalImage.getColorModel().getRed(data);
					blue = originalImage.getColorModel().getBlue(data);
					green = originalImage.getColorModel().getGreen(data);
					heights[i][j]=red;
				}
				
			}
		return heights;
		   
	}
	public static Color[][] getGrayPicture(String filename,int minX,int minY,int maxX,int maxY) throws FileNotFoundException, IOException
	{	
		File file =new File(filename);
		BufferedImage originalImage=ImageIO.read(new FileInputStream(file));
		originalImage.getColorModel();
		System.out.println(originalImage.getColorModel());
		
		int green=0,red=0,blue=0,rgb;
		int imageWidth = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		Color[][] heights=new Color[maxX-minX ][maxY-minY ];
		for(int i = minX;i < maxX ;i++)
			{
				for(int j = minY;j < maxX ;j++)
				{
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
					Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ
					
					red = originalImage.getColorModel().getRed(data);
					if(red!=0){
					blue = originalImage.getColorModel().getBlue(data);
					green = originalImage.getColorModel().getGreen(data);
					heights[i-minX][j-minY]=new Color(red,blue,green);
					}
				}
				
			}
		return heights;
		   
	}
	
	public static int[][] getColorPicture(String filename) throws FileNotFoundException, IOException
	{	
		File file =new File(filename);
		BufferedImage originalImage=ImageIO.read(new FileInputStream(file));
		originalImage.getColorModel();
		System.out.println(originalImage.getColorModel());
		
		int green=0,red=0,blue=0,rgb;
		int imageWidth = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		int[][] heights=new int[imageWidth][imageHeight];
		for(int i = originalImage.getMinX();i < imageWidth ;i++)
			{
				for(int j = originalImage.getMinY();j < imageHeight ;j++)
				{
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
					Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ
				
					red = originalImage.getColorModel().getRed(data);
					blue = originalImage.getColorModel().getBlue(data);
					green = originalImage.getColorModel().getGreen(data);
					heights[i][j]=red;
				}
				
			}
		return heights;
		   
	}
	public static void main(String args[]){
		File file =new File("D:/graymap.png");
		File file2 =new File("D:/graymap1.png");
		try {
			ImageUtil t =new ImageUtil();
			
			BufferedImage bi=ImageIO.read(new FileInputStream(file));
		//	BufferedImage binew= t.getGrayPictureAPI(bi);
			t.getGrayPicture(bi);
			//ImageIO.write(binew, "png", new FileOutputStream(file2));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getGrayPicture(BufferedImage originalImage)
	{	
		originalImage.getColorModel();
		System.out.println(originalImage.getColorModel());
		int green=0,red=0,blue=0,rgb;
		int imageWidth = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		for(int i = originalImage.getMinX();i < imageWidth ;i++)
			{
				for(int j = originalImage.getMinY();j < imageHeight ;j++)
				{
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
					Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ
				
					red = originalImage.getColorModel().getRed(data);
					blue = originalImage.getColorModel().getBlue(data);
					green = originalImage.getColorModel().getGreen(data);
					red = (red*3 + green*6 + blue*1)/10;
					green = red;
					blue = green;
/*
���ｫr��g��b��ת��Ϊrgbֵ����ΪbufferedImageû���ṩ���õ�����ɫ�ķ�����ֻ������rgb��rgb���Ϊ8388608�����������ֵʱ��Ӧ��ȥ255*255*255��16777216
*/
					rgb = (red*256 + green)*256+blue;
					if(rgb>8388608)
					{
						rgb = rgb - 16777216;
					}
//��rgbֵд��ͼƬ
					System.out.printf(" %d %d %d \r\n",red,blue,green);
				}
				
			}
		   
        return originalImage;   
	}
	
	public BufferedImage getGrayPictureAPI(BufferedImage originalImage)
	{
		BufferedImage grayPicture;
		int imageWidth = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		
		grayPicture = new BufferedImage(imageWidth, imageHeight,   
                BufferedImage.TYPE_3BYTE_BGR);
		ColorConvertOp cco = new ColorConvertOp(ColorSpace   
                .getInstance(ColorSpace.CS_GRAY), null);   
        cco.filter(originalImage, grayPicture);   
        return grayPicture;   
	}

}
