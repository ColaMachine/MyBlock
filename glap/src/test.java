import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.common.collect.Lists;


public class test {
	public static void main(String args[]){
		System.out.println((int)0.999);;
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
//ͼƬ�����ص���ʵ�Ǹ������������}��forѭ��4��ÿ�����ؽ��в���
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
