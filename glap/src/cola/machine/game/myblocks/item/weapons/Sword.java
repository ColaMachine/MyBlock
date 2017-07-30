package cola.machine.game.myblocks.item.weapons;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.dozenx.util.ImageUtil;
import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.ColorBlock;

public class Sword {
	public Block[] swords;
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public String name = "sword";

	public void init() {
		List<Block> list = new ArrayList<Block>();
		try {
			Color[][] colors = ImageUtil.getGrayPicture("assets/images/items.png", 64,
					64, 80, 80);
			for (int i = 0; i < 16; i++)
				for (int j = 0; j < 16; j++) {
					Color color = colors[i][j];
					if (color != null)
					{

							Block soil = new ColorBlock(i, 0, j, color);
							list.add(soil);
					}
				}
			swords = list.toArray(new Block[list.size()]);

		} catch (FileNotFoundException e) {
			// VIP Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// VIP Auto-generated catch block
			e.printStackTrace();
		}
	}

    public void init1 () {
        List<Block> list = new ArrayList<Block>();
        try {
            Color[][] colors = ImageUtil.getGrayPicture("assets/images/items.png", 64,
                    64, 80, 80);
            for (int i = 0; i < 16; i++)
                for (int j = 0; j < 16; j++) {
                    Color color = colors[i][j];
                    if (color != null)
                    {

                        Block soil = new ColorBlock(i, 0, j, color);
                        list.add(soil);
                    }
                }
            swords = list.toArray(new Block[list.size()]);

        } catch (FileNotFoundException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }

	public void render() {
		// 先缩小
		//GL11.glRotated(180, 0, 1, 0);
	
		//GL11.glTranslatef(x-2,y, z);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);//GL11.glTranslatef(2,0f,-4f);	
		GL11.glRotated(135, 1,0 , 0);
		GL11.glRotated(90, 0, 0, 1);
		
		GL11.glScalef(0.2f, 0.2f, 0.2f);
		GL11.glTranslatef(-3.5f,-0.5f, -11f);
		for (Block block : swords) {
            ColorBlock colorBlock = (ColorBlock)block;
			GL11.glColor3f(colorBlock.r() / 256f, colorBlock.b() / 256f, colorBlock.g() / 256f);
			block.render();
		}
		GL11.glTranslatef(3.5f,0.5f,11f);
		GL11.glScalef(5f,5f, 5f);
		
		GL11.glRotated(-90, 0, 0, 1);
		GL11.glRotated(-135, 1, 0, 0);//GL11.glTranslatef(-2f,0f,4f);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glTranslatef(-x+2,-y, -z);
		//GL11.glRotated(-180, 0, 1, 0);
	}

	public Sword(int x, int y, int z) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.init();
	}
	public void with(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
