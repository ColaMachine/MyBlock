import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


public class test {
	public static void main(String args[]){
		File file =new File("D:/graymap.png");
		File file2 =new File("D:/graymap1.png");
		try {
			test t =new test();
			
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
//图片的像素点其实是个矩阵，这里利用两个for循环来对每个像素进行操作
					Object data = originalImage.getRaster().getDataElements(i, j, null);//获取该点像素，并以object类型表示
				
					red = originalImage.getColorModel().getRed(data);
					blue = originalImage.getColorModel().getBlue(data);
					green = originalImage.getColorModel().getGreen(data);
					red = (red*3 + green*6 + blue*1)/10;
					green = red;
					blue = green;
/*
这里将r、g、b再转化为rgb值，因为bufferedImage没有提供设置单个颜色的方法，只能设置rgb。rgb最大为8388608，当大于这个值时，应减去255*255*255即16777216
*/
					rgb = (red*256 + green)*256+blue;
					if(rgb>8388608)
					{
						rgb = rgb - 16777216;
					}
//将rgb值写回图片
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
