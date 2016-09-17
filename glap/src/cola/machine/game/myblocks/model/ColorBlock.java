package cola.machine.game.myblocks.model;

import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.model.AABB.AABB;

public class ColorBlock extends AABB implements Block {
	public int x = 0;
	public int y = 0;
	public int z = 0;
	public int red = 0;
	public int blue = 0;
	public int green = 0;
    public float rf =0;
    public float bf=0;
    public float gf=0;

    public boolean zh=true;
    public boolean zl=true;
    public boolean yl=true;



    public boolean yh=true;
    public boolean xl=true;
    public boolean xh=true;
    public boolean isZh() {
        return zh;
    }

    public void setZh(boolean zh) {
        this.zh = zh;
    }

    public boolean isZl() {
        return zl;
    }

    public void setZl(boolean zl) {
        this.zl = zl;
    }

    public boolean isYl() {
        return yl;
    }

    public void setYl(boolean yl) {
        this.yl = yl;
    }

    public boolean isYh() {
        return yh;
    }

    public void setYh(boolean yh) {
        this.yh = yh;
    }

    public boolean isXl() {
        return xl;
    }

    public void setXl(boolean xl) {
        this.xl = xl;
    }

    public boolean isXh() {
        return xh;
    }

    public void setXh(boolean xh) {
        this.xh = xh;
    }
    public float rf(){
        return this.rf;
    }
    public float bf(){
        return this.bf;
    }
    public float gf(){
        return this.gf;
    }
	public ColorBlock(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.minX = x;
		this.minY = y;
		this.minZ = z;
		this.maxX = x + 1;
		this.maxY = y + 1;
		this.maxZ = z + 1;
	}

	public ColorBlock(int x, int y, int z, Color color) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.minX = x;
		this.minY = y;
		this.minZ = z;
		this.maxX = x + 1;
		this.maxY = y + 1;
		this.maxZ = z + 1;
		this.red = color.r();
		this.blue = color.b();
		this.green = color.g();
        this.rf=this.red*1f/256;
        this.gf=this.green*1f/256;
        this.bf=this.blue*1f/256;
	}

	public void render() {
		// Front Face



		GL11.glBegin(GL11.GL_QUADS);
        if(zh){
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);
		GL11.glVertex3f(minX, minY, maxZ); // Bottom Left
		GL11.glVertex3f(maxX, minY, maxZ); // Bottom Right
		GL11.glVertex3f(maxX, maxY, maxZ); // Top Right
		GL11.glVertex3f(minX, maxY, maxZ); // Top Left
        }
		// Back Face
        if(zl) {
            GL11.glNormal3f(0.0f, 0.0f, -1.0f);
            GL11.glVertex3f(minX, minY, minZ); // Bottom Right
            GL11.glVertex3f(minX, maxY, minZ); // Top Right
            GL11.glVertex3f(maxX, maxY, minZ); // Top Left
            GL11.glVertex3f(maxX, minY, minZ); // Bottom Left
        }
		// Top Face
		// Top Face
        if(yh) {
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glVertex3f(minX, maxY, minZ); // Top Left
            GL11.glVertex3f(minX, maxY, maxZ); // Bottom Left
            GL11.glVertex3f(maxX, maxY, maxZ); // Bottom Right
            GL11.glVertex3f(maxX, maxY, minZ); // Top Right
        }
        if(yl) {
            // Bottom Face
            GL11.glNormal3f(0.0f, -1.0f, 0.0f);
            GL11.glVertex3f(minX, minY, minZ); // Top Right
            GL11.glVertex3f(maxX, minY, minZ); // Top Left
            GL11.glVertex3f(maxX, minY, maxZ); // Bottom Left
            GL11.glVertex3f(minX, minY, maxZ); // Bottom Right
        }
		// Right face
        if(xh) {
            GL11.glNormal3f(1.0f, 0.0f, 0.0f);
            GL11.glVertex3f(maxX, minY, minZ); // Bottom Right
            GL11.glVertex3f(maxX, maxY, minZ); // Top Right
            GL11.glVertex3f(maxX, maxY, maxZ); // Top Left
            GL11.glVertex3f(maxX, minY, maxZ); // Bottom Left
        }
		// Left Face
        if(xl) {
            GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
            GL11.glVertex3f(minX, minY, minZ); // Bottom Left
            GL11.glVertex3f(minX, minY, maxZ); // Bottom Right
            GL11.glVertex3f(minX, maxY, maxZ); // Top Right
            GL11.glVertex3f(minX, maxY, minZ); // Top Left
        }
		GL11.glEnd();
	}

	public void setCenter(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.minX = x - 1;
		this.minY = y - 1;
		this.maxX = x + 1;
		this.maxY = y + 1;
	}

	@Override
	public int getX() {
		// VIP Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// VIP Auto-generated method stub
		return y;
	}

	@Override
	public int getZ() {
		// VIP Auto-generated method stub
		return z;
	}

	public int r() {
		return red;
	}

	@Override
	public int b() {
		return blue;
	}

	@Override
	public int g() {
		return green;
	}

	@Override
	public String getName() {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public void renderCube() {
		// VIP Auto-generated method stub
		
	}

	@Override
	public void renderColor() {
		// VIP Auto-generated method stub
		
	}

	@Override
	public int getId() {
		// VIP Auto-generated method stub
		return 0;
	}
    public boolean getAlpha(){
        return false;
    }
}
