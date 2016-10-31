package cola.machine.game.myblocks.world.chunks.Internal;

import cola.machine.game.myblocks.block.BlockDefManager;
import cola.machine.game.myblocks.engine.modes.StartMenuState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.world.chunks.*;
import glapp.GLApp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Region3i;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraDenseArray16Bit;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class ChunkImpl implements Chunk {
	int currentBlockType = 0;
	int faceIndex = 0;
	public int displayId = 0;
    public int alphaDisplayId=0;
	public ChunkImpl(Vector3i chunkPos) {
		this(chunkPos.x, chunkPos.y, chunkPos.z);
	}

	private final Vector3i chunkPos = new Vector3i();
	private BlockManager blockManager;
	private Region3i region;

	public ChunkImpl() {
		blockData = new TeraDenseArray16Bit(getChunkSizeX(), getChunkSizeY(),
				getChunkSizeZ());
		// sunlightData =
		// c.getSunlightDataEntry().factory.create(getChunkSizeX(),
		// getChunkSizeY(), getChunkSizeZ());
		// lightData = c.getLightDataEntry().factory.create(getChunkSizeX(),
		// getChunkSizeY(), getChunkSizeZ());
		// extraData = c.getExtraDataEntry().factory.create(getChunkSizeX(),
		// getChunkSizeY(), getChunkSizeZ());
		blockManager = CoreRegistry.get(BlockManager.class);
	}

	public ChunkImpl(int x, int y, int z) {
		this();
		chunkPos.x = x;
		chunkPos.y = y;
		chunkPos.z = z;
		region = Region3i.createFromMinAndSize(new Vector3i(x
				* ChunkConstants.SIZE_X, y * ChunkConstants.SIZE_Y, z
				* ChunkConstants.SIZE_Z), ChunkConstants.CHUNK_SIZE);
	}

	@Override
	public Vector3i getPos() {
		// VIP Auto-generated method stub
		return this.chunkPos;
	}

	@Override
	public Block getBlock(Vector3i pos) {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		// VIP Auto-generated method stub
		return null;
	}

	public TeraArray blockData;

	@Override
	public Block setBlock(int x, int y, int z, Block block) {
		int oldValue = blockData.set(x, y, z, block.getId());
		if (oldValue != block.getId()) {
			/*
			 * if (!block.isLiquid()) { setLiquid(x, y, z, new LiquidData()); }
			 */
		}
		return blockManager.getBlock((short) oldValue);
	}

	TextureInfo ti = TextureManager.getTextureInfo("mantle");

    /**
     * 判断对方和自己 如果一个是alpha 一个是非透明的 就需要
     * @param selfType
     * @param blockType
     * @return
     */
	public boolean needToPaint(int selfType,int blockType){
		if( blockType==0||selfType==0){
			return true;
		}
//如果一个是透明的 另一个是不透明的 就需要绘制
        //如果两个都是不透明的就不需要绘制
        if((!blockManager.getBlock(selfType).getAlpha() && !blockManager.getBlock(blockType).getAlpha()) ){
            return false;
        }else if(blockManager.getBlock(selfType).getAlpha() && blockManager.getBlock(blockType).getAlpha()){
            return false;
        }else{
            return true;
        }
		/*if((selfType==6||selfType==3) && blockType==0){
			return true;
		}
		if(selfType!=6 && (blockType==0|| blockType==6|| blockType==3)){
			return true;
		}*/
		
		//return false;
	}
    public boolean judgeAlpha(int type){
        if(3==type|| 0==type|| 6==type){
            return true;
        }return false;
    }

    public void buildAlpha() {

        alphaDisplayId = GLApp.beginDisplayList();

        // block.render();
       // GL11.glBegin(GL11.GL_QUADS);
        for (int x = 0; x < this.getChunkSizeX(); x++) {
            for (int z = 0; z < this.getChunkSizeZ(); z++) {
                for (int y = 0; y < this.getChunkSizeY(); y++) {
                    int i =0;
                    try{
                        i = blockData.get(x, y, z);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(i!=6&&i!=3)
                        continue;
                    currentBlockType = i;


                    if ( i>0) {// System.out.printf("%d %d %d /n\n",x,y,z);
                        // 判断上面
                        if (y < this.getChunkSizeY() - 1) {
                            if (needToPaint(i,blockData.get(x, y + 1, z) )) {
                                addThisTop(x, y, z);
                            } else {

                            }
                        } else {
                            addThisTop(x, y, z);
                        }

                        // 判断下面
                        if (y > 0) {
                            if (needToPaint(i,blockData.get(x, y - 1, z) )) {
                                addThisBottom(x, y, z);
                            }
                        } else {
                            addThisBottom(x, y, z);
                        }

                        // 判断左面
                        if (x > 0) {
                            if (needToPaint(i,blockData.get(x - 1, y, z) )) {
                                addThisLeft(x, y, z);
                            }
                        } else {
                            // TODO
                            addThisLeft(x, y, z);
                        }

                        // 判断右面

                        if (x < this.getChunkSizeX() - 1) {
                            if (needToPaint(i,blockData.get(x + 1, y, z))) {
                                addThisRight(x, y, z);
                            }
                        } else {// TODO
                            addThisRight(x, y, z);
                        }

                        // 前面

                        if (z < this.getChunkSizeZ() - 1) {
                            if (needToPaint(i,blockData.get(x, y, z + 1) )) {
                                addThisFront(x, y, z);
                            }
                        } else {// TODO
                            addThisFront(x, y, z);
                        }

                        // 后面

                        if (z > 0) {
                            if (needToPaint(i,blockData.get(x, y, z - 1) )) {
                                addThisBack(x, y, z);
                            }
                        } else {// TODO
                            addThisBack(x, y, z);
                        }

                    }

                }
            }
        }  // GL11.glEnd();
        //System.out.println(this.count);
        GLApp.endDisplayList();
    }
	public void build() {

		displayId = GLApp.beginDisplayList();
        ChunkProvider chunkProvider = CoreRegistry.get(ChunkProvider.class);
        ChunkImpl leftChunk = (ChunkImpl)chunkProvider.getChunk(this.chunkPos.x-1,this.chunkPos.y,this.chunkPos.z);
        ChunkImpl rightChunk = (ChunkImpl)chunkProvider.getChunk(this.chunkPos.x+1,this.chunkPos.y,this.chunkPos.z);
        ChunkImpl frontChunk = (ChunkImpl)chunkProvider.getChunk(this.chunkPos.x,this.chunkPos.y,this.chunkPos.z+1);
        ChunkImpl backChunk =(ChunkImpl) chunkProvider.getChunk(this.chunkPos.x,this.chunkPos.y,this.chunkPos.z-1);

		// block.render();
        //GL11.glBegin(GL11.GL_QUADS);

		for (int x = 0; x < this.getChunkSizeX(); x++) {
			for (int z = 0; z < this.getChunkSizeZ(); z++) {
				for (int y = 0; y < this.getChunkSizeY(); y++) {
					int i =0;
					try{
					 i = blockData.get(x, y, z);
					}catch(Exception e){
						e.printStackTrace();
					}
                    /*if(i==6||i==3)
                        continue;*/
					currentBlockType = i;

					if (i > 0) {// System.out.printf("%d %d %d /n\n",x,y,z);
						// 判断上面
						if (y < this.getChunkSizeY() - 1) {
							if (needToPaint(i,blockData.get(x, y + 1, z) )) {
								addThisTop(x, y, z);
                               // System.out.println("top");
							} else {

							}
						} else {
							addThisTop(x, y, z);//System.out.println("top");
						}

						// 判断下面
						if (y > 0) {
							if (needToPaint(i,blockData.get(x, y - 1, z) )) {
								addThisBottom(x, y, z);//System.out.println("Bottom");
							}
						} else {
							//addThisBottom(x, y, z);//System.out.println("Bottom");
						}

						// 判断左面
						if (x > 0) {
							if (needToPaint(i,blockData.get(x - 1, y, z) )) {
								addThisLeft(x, y, z);//System.out.println("left");
							}
						} else {
							// TODO//x
                            if(leftChunk!=null)
                            if (needToPaint(i,  leftChunk.blockData.get(leftChunk.getChunkSizeX()-1,y,z))) {
                                addThisLeft(x, y, z);//System.out.println("left");
                            }else{
                                //LogUtil.println("bu xu yao hui zhi");
                            }


						}

						// 判断右面

						if (x < this.getChunkSizeX() - 1) {
							if (needToPaint(i,blockData.get(x + 1, y, z))) {
								addThisRight(x, y, z);//System.out.println("Right");
							}
						} else {// TODO

                            if(rightChunk!=null)
                            if (needToPaint(i,  rightChunk.blockData.get(0,y,z))) {
                                addThisRight(x, y, z);//System.out.println("left");
                            }
							//addThisRight(x, y, z);//System.out.println("Right");
						}

						// 前面

						if (z < this.getChunkSizeZ() - 1) {
							if (needToPaint(i,blockData.get(x, y, z + 1) )) {
								addThisFront(x, y, z);//System.out.println("Front");
							}
						} else {// TODO
                            if(frontChunk!=null)
                                if (needToPaint(i,  frontChunk.blockData.get(x,y,0))) {
                                    addThisFront(x, y, z);//System.out.println("left");
                                }
							//addThisFront(x, y, z);//System.out.println("Front");
						}

						// 后面

						if (z > 0) {
							if (needToPaint(i,blockData.get(x, y, z - 1) )) {
								addThisBack(x, y, z);//System.out.println("back");
							}
						} else {// TODO z==0
                            if(backChunk!=null)
                                if (needToPaint(i,  backChunk.blockData.get(x,y,this.getChunkSizeZ()-1))) {
                                    addThisBack(x, y, z);//System.out.println("left");
                                }
							//addThisBack(x, y, z);//System.out.println("back");
						}

					}

				}
			}
		}
       // GL11.glEnd();
		//System.out.println(this.count);
		GLApp.endDisplayList();
	}
    int VaoId;
    int VboId;
    public void CreateTerrainVBO(){
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects


        veticesBuffer.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
        VboId=glGenBuffers();//create vbo


        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, veticesBuffer, GL_STATIC_DRAW);//put data

        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * 4, 3*4);

        Util.checkGLError();
        glEnableVertexAttribArray(1);
        Util.checkGLError();




    }
    public void CreateTerrainVAO(){


        VaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(VaoId);
        Util.checkGLError();
        this.CreateTerrainVBO();

        glBindVertexArray(0);
        Util.checkGLError();
    }
    public void buildShaderBuffer() {


        ChunkProvider chunkProvider = CoreRegistry.get(ChunkProvider.class);
        ChunkImpl leftChunk = (ChunkImpl)chunkProvider.getChunk(this.chunkPos.x-1,this.chunkPos.y,this.chunkPos.z);
        ChunkImpl rightChunk = (ChunkImpl)chunkProvider.getChunk(this.chunkPos.x+1,this.chunkPos.y,this.chunkPos.z);
        ChunkImpl frontChunk = (ChunkImpl)chunkProvider.getChunk(this.chunkPos.x,this.chunkPos.y,this.chunkPos.z+1);
        ChunkImpl backChunk =(ChunkImpl) chunkProvider.getChunk(this.chunkPos.x,this.chunkPos.y,this.chunkPos.z-1);

        // block.render();
        //GL11.glBegin(GL11.GL_QUADS);

        for (int x = 0; x < this.getChunkSizeX(); x++) {
            for (int z = 0; z < this.getChunkSizeZ(); z++) {
                for (int y = 0; y < this.getChunkSizeY(); y++) {
                    int i =0;
                    try{
                        i = blockData.get(x, y, z);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    /*if(i==6||i==3)
                        continue;*/
                    currentBlockType = i;

                    if (i > 0) {// System.out.printf("%d %d %d /n\n",x,y,z);
                        // 判断上面
                        if (y < this.getChunkSizeY() - 1) {
                            if (needToPaint(i,blockData.get(x, y + 1, z) )) {
                                addThisTop2(x, y, z);
                                // System.out.println("top");
                            } else {

                            }
                        } else {
                            addThisTop2(x, y, z);//System.out.println("top");
                        }

                        // 判断下面
                        if (y > 0) {
                            if (needToPaint(i,blockData.get(x, y - 1, z) )) {
                                addThisBottom2(x, y, z);//System.out.println("Bottom");
                            }
                        } else {
                            //addThisBottom(x, y, z);//System.out.println("Bottom");
                        }

                        // 判断左面
                        if (x > 0) {
                            if (needToPaint(i,blockData.get(x - 1, y, z) )) {
                                addThisLeft2(x, y, z);//System.out.println("left");
                            }
                        } else {
                            // TODO//x
                            if(leftChunk!=null)
                                if (needToPaint(i,  leftChunk.blockData.get(leftChunk.getChunkSizeX()-1,y,z))) {
                                    addThisLeft2(x, y, z);//System.out.println("left");
                                }else{
                                    //LogUtil.println("bu xu yao hui zhi");
                                }


                        }

                        // 判断右面

                        if (x < this.getChunkSizeX() - 1) {
                            if (needToPaint(i,blockData.get(x + 1, y, z))) {
                                addThisRight2(x, y, z);//System.out.println("Right");
                            }
                        } else {// TODO

                            if(rightChunk!=null)
                                if (needToPaint(i,  rightChunk.blockData.get(0,y,z))) {
                                    addThisRight2(x, y, z);//System.out.println("left");
                                }
                            //addThisRight(x, y, z);//System.out.println("Right");
                        }

                        // 前面

                        if (z < this.getChunkSizeZ() - 1) {
                            if (needToPaint(i,blockData.get(x, y, z + 1) )) {
                                addThisFront2(x, y, z);//System.out.println("Front");
                            }
                        } else {// TODO
                            if(frontChunk!=null)
                                if (needToPaint(i,  frontChunk.blockData.get(x,y,0))) {
                                    addThisFront2(x, y, z);//System.out.println("left");
                                }
                            //addThisFront(x, y, z);//System.out.println("Front");
                        }

                        // 后面

                        if (z > 0) {
                            if (needToPaint(i,blockData.get(x, y, z - 1) )) {
                                addThisBack2(x, y, z);//System.out.println("back");
                            }
                        } else {// TODO z==0
                            if(backChunk!=null)
                                if (needToPaint(i,  backChunk.blockData.get(x,y,this.getChunkSizeZ()-1))) {
                                    addThisBack2(x, y, z);//System.out.println("left");
                                }
                            //addThisBack(x, y, z);//System.out.println("back");
                        }

                    }

                }
            }
        }
        CreateTerrainVAO();
        // GL11.glEnd();
        //System.out.println(this.count);
    }

	public IntBuffer vetices = BufferUtils.createIntBuffer(14);
	public int count = 0;
	public IntBuffer normalizes = BufferUtils.createIntBuffer(4);
	public FloatBuffer veticesBuffer = BufferUtils.createFloatBuffer(196608);
	public void addThisTop2(int x, int y, int z){
		this.faceIndex = 1;
		count++;//left front top
		veticesBuffer.put(x);
		veticesBuffer.put(y + 1);
		veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.minX);
		veticesBuffer.put(ti.minY);

		veticesBuffer.put(x + 1); //left front right
		veticesBuffer.put(y + 1);
		veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.maxX);
		veticesBuffer.put(ti.minY);

		veticesBuffer.put(x + 1);//left behind right
		veticesBuffer.put(y + 1);
		veticesBuffer.put(z);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.maxX);
		veticesBuffer.put(ti.maxY);

		veticesBuffer.put(x);
		veticesBuffer.put(y + 1);
		veticesBuffer.put(z);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(0);

		veticesBuffer.put(ti.minX);
		veticesBuffer.put(ti.maxY);

		veticesBuffer.put(x);
		veticesBuffer.put(y + 1);
		veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(0);

		veticesBuffer.put(ti.minX);
		veticesBuffer.put(ti.minY);


		veticesBuffer.put(x + 1); //left behind right
		veticesBuffer.put(y + 1);
		veticesBuffer.put(z);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.maxX);
		veticesBuffer.put(ti.maxY);
	}
	public void addThisTop(int x, int y, int z) {
		this.faceIndex = 1;
		count++;
		vetices.put(x);
		vetices.put(y + 1);
		vetices.put(z + 1);

		vetices.put(x + 1);
		vetices.put(y + 1);
		vetices.put(z + 1);

		vetices.put(x + 1);
		vetices.put(y + 1);
		vetices.put(z);

		vetices.put(x);
		vetices.put(y + 1);
		vetices.put(z);

		normalizes.put(0);
		normalizes.put(1);
		normalizes.put(0);
		Draw();
	}

	public static void main(String[] args) {
		IntBuffer vetices1 = BufferUtils.createIntBuffer(16);
		vetices1.put(1);
		vetices1.put(3);
		vetices1.position(vetices1.position() - 1);
		System.out.println(vetices1.get());
		vetices1.put(2);
		vetices1.position(vetices1.position() - 1);
		System.out.println(vetices1.get());
	}
BlockDefManager blockDefManager;
	public void Draw() {// up down left right front back

boolean flat =true;
        //blockDefManager.getBlockById()
		switch (this.currentBlockType) {
        case 1:
			ti = TextureManager.getTextureInfo("stone");

					ti.bind();
			DrawVetext();
			break;
		case 3:
			if (faceIndex == 1) {
				ti = TextureManager.getTextureInfo("grass_top");
				ti.bind();
			} else if (faceIndex == 2) {
				ti = TextureManager.getTextureInfo("soil");
				ti.bind();
			} else {
				ti = TextureManager.getTextureInfo("grass_side");
				ti.bind();
			}DrawVetext();
			break;
		case 20:
			ti = TextureManager.getTextureInfo("glass");
			ti.bind();;DrawVetext();
			break;
        case 8:
                ti = TextureManager.getTextureInfo("water");
                if (faceIndex == 1) {
					ti.bind();;DrawVetext();
                }
                break;
		case 12:
			ti = TextureManager.getTextureInfo("sand");
			ti.bind();;DrawVetext();
			break;
		case 7:
			ti = TextureManager.getTextureInfo("mantle");
			ti.bind();;DrawVetext();
			break;

		case 5:
			ti = TextureManager.getTextureInfo("wood");
			ti.bind();;DrawVetext();
			break;
		default:
			System.out.println("添纹理的时候 什么都没对应上");
		}
		// GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
		// GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		// GL11.glBegin(GL11.GL_QUADS);
		
		// GL11.glEnd();
		normalizes.position(0);
		vetices.position(0);
	}
	public void DrawVetext(){
		vetices.position(vetices.position() - 12);
		normalizes.position(normalizes.position() - 3);

		GL11.glNormal3f(normalizes.get(), normalizes.get(), normalizes.get());

		GL11.glTexCoord2f(ti.minX, ti.minY);
		GL11.glVertex3d(vetices.get(), vetices.get(), vetices.get());
		GL11.glTexCoord2f(ti.maxX, ti.minY);
		GL11.glVertex3d(vetices.get(), vetices.get(), vetices.get());
		GL11.glTexCoord2f(ti.maxX, ti.maxY);
		GL11.glVertex3d(vetices.get(), vetices.get(), vetices.get());
		GL11.glTexCoord2f(ti.minX, ti.maxY);
		GL11.glVertex3d(vetices.get(), vetices.get(), vetices.get());
		// vetices.flip();
		
	}
	public void addThisBottom2(int x, int y, int z) {
		this.faceIndex = 2;
		count++;
		veticesBuffer.put(x);
		veticesBuffer.put(y);
		veticesBuffer.put(z);
		veticesBuffer.put(0);
		veticesBuffer.put(-1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.minX);
		veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y);
        veticesBuffer.put(z);
		veticesBuffer.put(0);
		veticesBuffer.put(-1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.maxX);
		veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y);
        veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(-1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.maxX);
		veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x);
        veticesBuffer.put(y);
        veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(-1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.minX);
		veticesBuffer.put(ti.maxY);


		veticesBuffer.put(x);
		veticesBuffer.put(y);
		veticesBuffer.put(z);
		veticesBuffer.put(0);
		veticesBuffer.put(-1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.minX);
		veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y);
        veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(-1);
		veticesBuffer.put(0);
		veticesBuffer.put(ti.maxX);
		veticesBuffer.put(ti.maxY);



	}
	public void addThisBottom(int x, int y, int z) {
		this.faceIndex = 2;
		count++;
		vetices.put(x);
		vetices.put(y);
		vetices.put(z);

		vetices.put(x + 1);
		vetices.put(y);
		vetices.put(z);

		vetices.put(x + 1);
		vetices.put(y);
		vetices.put(z + 1);

		vetices.put(x);
		vetices.put(y);
		vetices.put(z + 1);
		normalizes.put(0);
		normalizes.put(-1);
		normalizes.put(0);

		Draw();

	}

	public void addThisFront(int x, int y, int z) {
		this.faceIndex = 5;
		vetices.put(x);
		count++;
		vetices.put(y);
		vetices.put(z + 1);

		vetices.put(x + 1);
		vetices.put(y);
		vetices.put(z + 1);

		vetices.put(x + 1);
		vetices.put(y + 1);
		vetices.put(z + 1);

		vetices.put(x);
		vetices.put(y + 1);
		vetices.put(z + 1);

		normalizes.put(0);
		normalizes.put(0);
		normalizes.put(1);
		Draw();
	}
	public void addThisFront2(int x, int y, int z) {
		this.faceIndex = 5;
		count++;

		veticesBuffer.put(x);
		veticesBuffer.put(y);
		veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(ti.minX);
		veticesBuffer.put(ti.minY);

		veticesBuffer.put(x + 1);
		veticesBuffer.put(y);
		veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(ti.maxX);
		veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(ti.maxX);
		veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
		veticesBuffer.put(0);
		veticesBuffer.put(0);
		veticesBuffer.put(1);
		veticesBuffer.put(ti.minX);
		veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x);
        veticesBuffer.put(y);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);


        veticesBuffer.put(x + 1);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);


		//Draw();
	}

	public void addThisBack(int x, int y, int z) {
		this.faceIndex = 6;
		count++;

		vetices.put(x + 1);
		vetices.put(y);
		vetices.put(z);

		vetices.put(x);
		vetices.put(y);
		vetices.put(z);

		vetices.put(x);
		vetices.put(y + 1);
		vetices.put(z);

		vetices.put(x + 1);
		vetices.put(y + 1);
		vetices.put(z);

		normalizes.put(0);
		normalizes.put(0);
		normalizes.put(-1);
		Draw();
	}
    public void addThisBack2(int x, int y, int z) {
        this.faceIndex = 6;
        count++;

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(-1);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);

        veticesBuffer.put(x);
        veticesBuffer.put(y);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(-1);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(-1);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);

        vetices.put(x + 1);
        vetices.put(y + 1);
        vetices.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(-1);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.maxY);


        veticesBuffer.put(x + 1);
        veticesBuffer.put(y);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(-1);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);


        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(-1);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);

    }

	public void addThisLeft(int x, int y, int z) {
		this.faceIndex = 3;
		count++;
		vetices.put(x);
		vetices.put(y);
		vetices.put(z);

		vetices.put(x);
		vetices.put(y);
		vetices.put(z + 1);

		vetices.put(x);
		vetices.put(y + 1);
		vetices.put(z + 1);

		vetices.put(x);
		vetices.put(y + 1);
		vetices.put(z);
		normalizes.put(-1);
		normalizes.put(0);
		normalizes.put(0);
		Draw();
	}
    public void addThisLeft2(int x, int y, int z) {
        this.faceIndex = 3;
        count++;
        veticesBuffer.put(x);
        veticesBuffer.put(y);
        veticesBuffer.put(z);
        veticesBuffer.put(-1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);


        veticesBuffer.put(x);
        veticesBuffer.put(y);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(-1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.minY);

        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(-1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);

        vetices.put(x);
        vetices.put(y + 1);
        vetices.put(z);
        veticesBuffer.put(-1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x);
        veticesBuffer.put(y);
        veticesBuffer.put(z);
        veticesBuffer.put(-1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);

        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(-1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);

    }

	public void addThisRight(int x, int y, int z) {
		this.faceIndex = 4;
		count++;

		vetices.put(x + 1);
		vetices.put(y);
		vetices.put(z + 1);


		vetices.put(x + 1);
		vetices.put(y);
		vetices.put(z);

		vetices.put(x + 1);
		vetices.put(y + 1);
		vetices.put(z);

		vetices.put(x + 1);
		vetices.put(y + 1);
		vetices.put(z + 1);

		normalizes.put(1);
		normalizes.put(0);
		normalizes.put(0);
		Draw();

	}
    public void addThisRight2(int x, int y, int z) {
        this.faceIndex = 4;
        count++;

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);


        veticesBuffer.put(x + 1);
        veticesBuffer.put(y);
        veticesBuffer.put(z);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);

    }

	/*
	 * public List<Integer> list = new ArrayList(); public int count = 0; public
	 * List<Integer> normalizelist = new ArrayList();
	 * 
	 * public void addThisTop(int x, int y, int z) {this.faceIndex=1; count++;
	 * list.add(x); list.add(y + 1); list.add(z + 1);
	 * 
	 * list.add(x + 1); list.add(y + 1); list.add(z + 1);
	 * 
	 * list.add(x + 1); list.add(y + 1); list.add(z);
	 * 
	 * list.add(x); list.add(y + 1); list.add(z);
	 * 
	 * normalizelist.add(0); normalizelist.add(1); normalizelist.add(0);Draw();
	 * }
	 * 
	 * public void addThisBottom(int x, int y, int z) {this.faceIndex=2;
	 * count++; list.add(x); list.add(y); list.add(z);
	 * 
	 * list.add(x + 1); list.add(y); list.add(z);
	 * 
	 * list.add(x + 1); list.add(y); list.add(z + 1);
	 * 
	 * list.add(x); list.add(y); list.add(z + 1); normalizelist.add(0);
	 * normalizelist.add(-1); normalizelist.add(0);Draw(); }
	 * 
	 * public void addThisFront(int x, int y, int z) { list.add(x); count++;
	 * list.add(y); list.add(z + 1);
	 * 
	 * list.add(x + 1); list.add(y); list.add(z + 1);
	 * 
	 * list.add(x + 1); list.add(y + 1); list.add(z + 1);
	 * 
	 * list.add(x); list.add(y + 1); list.add(z + 1);
	 * 
	 * normalizelist.add(0); normalizelist.add(0); normalizelist.add(1);Draw();
	 * 
	 * }
	 * 
	 * public void addThisBack(int x, int y, int z) { count++;
	 * 
	 * list.add(x + 1); list.add(y); list.add(z);
	 * 
	 * list.add(x); list.add(y); list.add(z);
	 * 
	 * list.add(x); list.add(y + 1); list.add(z);
	 * 
	 * list.add(x + 1); list.add(y + 1); list.add(z);
	 * 
	 * normalizelist.add(0); normalizelist.add(0); normalizelist.add(-1);Draw();
	 * 
	 * }
	 * 
	 * public void addThisLeft(int x, int y, int z) { count++; list.add(x);
	 * list.add(y); list.add(z);
	 * 
	 * list.add(x); list.add(y); list.add(z + 1);
	 * 
	 * list.add(x); list.add(y + 1); list.add(z + 1);
	 * 
	 * list.add(x); list.add(y + 1); list.add(z); normalizelist.add(-1);
	 * normalizelist.add(0); normalizelist.add(0);Draw(); }
	 * 
	 * public void addThisRight(int x, int y, int z) { count++;
	 * 
	 * list.add(x + 1); list.add(y); list.add(z);
	 * 
	 * list.add(x + 1); list.add(y + 1); list.add(z);
	 * 
	 * list.add(x + 1); list.add(y + 1); list.add(z + 1);
	 * 
	 * list.add(x + 1); list.add(y); list.add(z + 1);
	 * 
	 * normalizelist.add(1); normalizelist.add(0); normalizelist.add(0);
	 * 
	 * }
	 */

	public int getChunkWorldPosX() {
		return chunkPos.x * getChunkSizeX();
	}

	@Override
	public int getChunkWorldPosY() {
		return chunkPos.y * getChunkSizeY();
	}

	@Override
	public int getChunkWorldPosZ() {
		return chunkPos.z * getChunkSizeZ();
	}

	@Override
	public int getChunkSizeX() {
		return ChunkConstants.SIZE_X;
	}

	@Override
	public int getChunkSizeY() {
		return ChunkConstants.SIZE_Y;
	}

	@Override
	public int getChunkSizeZ() {
		return ChunkConstants.SIZE_Z;
	}

	@Override
	public Block setBlock(Vector3i pos, Block block) {
		// VIP Auto-generated method stub
		return null;
	}

	/*
	 * @Override public void setLiquid(Vector3i pos, LiquidData state) { // VIP
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void setLiquid(int x, int y, int z, LiquidData newState)
	 * { // VIP Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public LiquidData getLiquid(Vector3i pos) { // VIP
	 * Auto-generated method stub return null; }
	 * 
	 * @Override public LiquidData getLiquid(int x, int y, int z) { // VIP
	 * Auto-generated method stub return null; }
	 */
	@Override
	public Vector3i getChunkWorldPos() {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public Vector3i getBlockWorldPos(Vector3i blockPos) {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public Vector3i getBlockWorldPos(int x, int y, int z) {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public int getBlockWorldPosX(int x) {
		return x + getChunkWorldPosX();
	}

	@Override
	public int getBlockWorldPosY(int y) {
		return y + getChunkWorldPosY();
	}

	@Override
	public int getBlockWorldPosZ(int z) {
		return z + getChunkWorldPosZ();
	}

	public ChunkBlockIterator getBlockIterator() {
		return new ChunkBlockIteratorImpl(blockManager, getChunkWorldPos(),
				blockData);
	}

	public int getBlockData(int x, int y, int z) {
        if(x<0||y<0||z<0){
            GLApp.msg("ChunkImpl  getBlockData error");
            System.exit(0);
        }
		return blockData.get(x, y, z);
	}

	public boolean disposed = true;

	public void disposeMesh() {

		// if(this.displayId!=0){
		if (!disposed) {
			GL11.glDeleteLists(this.displayId, 1);
            GL11.glDeleteLists(this.alphaDisplayId, 1);
			// this.displayId=0;
			this.disposed = true;
			
			//保存数据
			String fileName =""+chunkPos.x +"_"+chunkPos.y+"_"+chunkPos.z+".chunk";
			 try {
                 Path path = PathManager.getInstance().getInstallPath().resolve("saves").resolve(fileName);
                 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toFile()));
                 this.blockData.writeExternal(out);
                 out.close();
             } catch (FileNotFoundException e) {
				// VIP Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// VIP Auto-generated catch block
				e.printStackTrace();
			}
			 this.blockData=null;
		} else {
			System.out.println("为什么要对没有初始化的chunkimpl取消");
		}
	}
    public void preRender2() {
        if (this.disposed) {
            // if(this.displayId==0){
            this.buildShaderBuffer();//this.buildAlpha();
            this.disposed = false;
        }
        if (this.VaoId == 0) {
            int error = GL11.glGetError();
            System.out.println("error: " + GLU.gluErrorString(error));
            System.out.println("displayId should not be 0 in preRender");
        }
        // GLApp.callDisplayList(this.displayId);
    }
	public void preRender() {
		if (this.disposed) {
			// if(this.displayId==0){
			this.build();//this.buildAlpha();
			this.disposed = false;
		}
		if (this.displayId == 0) {
			int error = GL11.glGetError();
			System.out.println("error: " + GLU.gluErrorString(error));
			System.out.println("displayId should not be 0 in preRender");
		}
		// GLApp.callDisplayList(this.displayId);
	}


    public void render2() {
        if (this.VaoId == 0) {
            // int error =GL11.glGetError();
            System.out.println(this.chunkPos+"displayId should not be 0 in render");
        } else {


            glUseProgram(StartMenuState.ProgramId);
            Util.checkGLError();
            glBindVertexArray(VaoId);
            Util.checkGLError();
            glDrawArrays(GL_TRIANGLES,0,this.count*6);
            Util.checkGLError();
            glBindVertexArray(0);
        }
    }
	public void render() {
		if (this.displayId == 0) {
			// int error =GL11.glGetError();
			System.out.println(this.chunkPos+"displayId should not be 0 in render");
		} else {

			GLApp.callDisplayList(this.displayId);
		}
	}

    public void renderAlpha(){
        if (this.alphaDisplayId == 0) {
            // int error =GL11.glGetError();
            System.out.println("displayId should not be 0 in render");
        } else {
            GLApp.callDisplayList(this.alphaDisplayId);
        }
    }






    public void save(){
        //保存数据
        String fileName =""+chunkPos.x +"_"+chunkPos.y+"_"+chunkPos.z+".chunk";
        try {
            Path path = PathManager.getInstance().getInstallPath().resolve("saves").resolve(fileName);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toFile()));
            this.blockData.writeExternal(out);
            out.close();
        } catch (FileNotFoundException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }
}
