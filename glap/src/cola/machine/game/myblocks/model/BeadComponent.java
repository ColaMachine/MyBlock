package cola.machine.game.myblocks.model;

import com.dozenx.game.engine.item.bean.ShapeType;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 16/7/23.
 */
public class BeadComponent extends Component{
    float secdis;
    float angle;
    float banjin;

    GL_Vector[] arr1;
    GL_Vector[] arr2;
    GL_Vector[] arr3;
    GL_Vector[] arr4;

    GL_Vector[] points;
    GL_Vector[] newPoints;
    public BeadComponent(float width, float height, float thick){
        super(width,height,thick);

        this.shapeType = ShapeType.ICON;
        secdis= height/secdis;
        arr1 = new GL_Vector[secnum];
        arr2 = new GL_Vector[secnum];
        arr3 = new GL_Vector[secnum];
        arr4 = new GL_Vector[secnum];
    }

    public void bend(int degree, int secnum){
        this.secnum =secnum;
        float degreeSec  = degree/secnum;
        GL_Vector[] axis = new GL_Vector[secnum+1];

        GL_Vector[] axisAfterBend = new GL_Vector[secnum+1];
        for(int i=0;i<secnum+1;i++){
            axis[i]=new GL_Vector();
            axisAfterBend[i]=new GL_Vector();
        }
        axis[0]=new GL_Vector((P1.x+P2.x)/2,P1.y,(P7.z+P5.z)/2);
        axisAfterBend[0].copy(axis[0]);
        axis[secnum]=new GL_Vector((P5.x+P6.x)/2,P1.y,(P7.z+P5.z)/2);
        axisAfterBend[secnum].copy(axis[secnum]);


        points =new GL_Vector[(secnum+1)*4];
        newPoints =new GL_Vector[(secnum+1)*4];
        newPoints[0] = points[0] =P1;
        newPoints[1] =points[1]=P2;
        newPoints[2] =points[2]=P3;
        newPoints[3] =points[3] =P4;

        float secY = (P5.y-P1.y)/secnum;
        for(int i=1;i<=secnum;i++){
            axis[i].set(axis[i-1].x, axis[i-1].y+secY,axis[i-1].z);
            axisAfterBend[i].copy(axis[i]);
        }

        //相对位置 vector

        for(int i=1;i<secnum;i++){

            points[4*i] =new GL_Vector(P1.x,P1.y+secY*i,P1.z);
            points[4*i+1]=new GL_Vector(P2.x,P2.y+secY*i,P2.z);
            points[4*i+2]=new GL_Vector(P3.x,P3.y+secY*i,P3.z);
            points[4*i+3] =new GL_Vector(P4.x,P4.y+secY*i,P4.z);


        }
        points[4*secnum+0]=P5;
        points[4*secnum+1] =P6;
        points[4*secnum+2]=P7;
        points[4*secnum+3] =P8;

        //计算弯曲后的位置
        for(int i=1 ;i<=secnum;i++){// 1 2   如果分成两段那么有 0 1 2
            GL_Vector vector = GL_Vector.sub(axis[i],axis[i-1]);
            GL_Vector rotationVector = GL_Vector.rotateWithZ(vector,degreeSec*i);
            GL_Vector newPoint = GL_Vector.add(axisAfterBend[i-1],rotationVector);
            axisAfterBend [i]= newPoint;



            for(int j=0 ;j<4;j++){//计算四个周点的新位置
                newPoints[4*i+j]=getNewPointAfterBend(points[i*4+j],axis[i],axisAfterBend[i],degreeSec*i);
            }



        }

        LogUtil.println("hello");



    }

    /**
     *
     * @param oldPotin 原来的点
     * @param oldaxis 原来的轴心
     * @param newAxis 新的轴心
     * @param degree 旋转的角度
     * @return
     */
    public GL_Vector getNewPointAfterBend(GL_Vector oldPotin,GL_Vector oldaxis,GL_Vector newAxis,float degree){
        GL_Vector oldVector = GL_Vector.sub(oldPotin,oldaxis);
        GL_Vector newVector = GL_Vector.rotateWithZ(oldVector,degree);
        GL_Vector newPoint = GL_Vector.add(newAxis,newVector);
        return newPoint;
    }

    int secnum;
    public void renderBend(){

//        GL11.glRotatef(rotateX, rotateY, rotateZ, 0);
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face





        for(int i=0;i<secnum;i++){
            GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
            GL11.glTexCoord2f(front.minX, front.minY);
            glVertex3fv(newPoints[0+4*i]);    // Bottom Left ǰ����
            GL11.glTexCoord2f(front.maxX, front.minY);
            glVertex3fv(newPoints[1+4*i]);    // Bottom Right ǰ����
            GL11.glTexCoord2f(front.maxX, front.maxY);
            glVertex3fv(newPoints[5+4*i]);    // Top Right ǰ����
            GL11.glTexCoord2f(front.minX, front.maxY);
            glVertex3fv(newPoints[4+4*i]);
        }


        // Back Face

        for(int i=0;i<secnum;i++){
            GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
            GL11.glTexCoord2f(front.minX, front.minY);
            glVertex3fv(newPoints[2+4*i]);    // Bottom Left ǰ����
            GL11.glTexCoord2f(front.maxX, front.minY);
            glVertex3fv(newPoints[3+4*i]);    // Bottom Right ǰ����
            GL11.glTexCoord2f(front.maxX, front.maxY);
            glVertex3fv(newPoints[7+4*i]);    // Top Right ǰ����
            GL11.glTexCoord2f(front.minX, front.maxY);
            glVertex3fv(newPoints[6+4*i]);
        }


        // left face


        for(int i=0;i<secnum;i++){
            GL11.glNormal3f(1.0f, 0.0f, 0.0f);
            GL11.glTexCoord2f(front.minX, front.minY);
            glVertex3fv(newPoints[1+4*i]);    // Bottom Left ǰ����
            GL11.glTexCoord2f(front.maxX, front.minY);
            glVertex3fv(newPoints[2+4*i]);    // Bottom Right ǰ����
            GL11.glTexCoord2f(front.maxX, front.maxY);
            glVertex3fv(newPoints[6+4*i]);    // Top Right ǰ����
            GL11.glTexCoord2f(front.minX, front.maxY);
            glVertex3fv(newPoints[5+4*i]);
        }

        // right Face

        for(int i=0;i<secnum;i++){
            GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
            GL11.glTexCoord2f(front.minX, front.minY);
            glVertex3fv(newPoints[3+4*i]);    // Bottom Left ǰ����
            GL11.glTexCoord2f(front.maxX, front.minY);
            glVertex3fv(newPoints[0+4*i]);    // Bottom Right ǰ����
            GL11.glTexCoord2f(front.maxX, front.maxY);
            glVertex3fv(newPoints[4+4*i]);    // Top Right ǰ����
            GL11.glTexCoord2f(front.minX, front.maxY);
            glVertex3fv(newPoints[7+4*i]);
        }
        GL11.glEnd();



    }
    public void setOffsetPosition(GL_Vector vector){

        this.offsetPosition.x= vector.x;
        this.offsetPosition.y= vector.y;
        this.offsetPosition.z= vector.z;


    }

}
