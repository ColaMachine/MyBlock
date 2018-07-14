package glmodel;

import cola.machine.game.myblocks.engine.Constants;
import org.lwjgl.BufferUtils;

import javax.vecmath.Point4f;
import java.nio.FloatBuffer;

/**
 * Define a 4x4 matrix, and provide functions to create common
 * matrices for 3D operations, such as rotate, scale and translate.
 */
public class GL_Matrix
{
	// vars hold a 4x4 matrix, defaults to identity matrix.
	public float m00=1, m01=0, m02=0, m03=0;
	public float m10=0, m11=1, m12=0, m13=0;
	public float m20=0, m21=0, m22=1, m23=0;
	public float m30=0, m31=0, m32=0, m33=1;
	
	/**
	 * Default to the identity matrix
	 */
	public GL_Matrix()
	{
	}
	
	/**
	 * Create a matrix with the three given axes
	 */
	public GL_Matrix(GL_Vector right, GL_Vector up, GL_Vector forward)
	{
		m00 = right.x;
		m10 = right.y;
		m20 = right.z;
		m01 = up.x;
		m11 = up.y;
		m21 = up.z;
		m02 = forward.x;
		m12 = forward.y;
		m22 = forward.z;
	}
	
	/**
	 * copy a two dimensional float array into this Matrix
	 */
	public void importFromArray(float[][] data)
	{
		// insure that the array is 4x4
		if (data.length<4) return;
		for (int i=0;i<4;i++) if (data[i].length<4) return;
		
		m00=data[0][0];  m01=data[0][1];  m02=data[0][2];  m03=data[0][3];
		m10=data[1][0];  m11=data[1][1];  m12=data[1][2];  m13=data[1][3];
		m20=data[2][0];  m21=data[2][1];  m22=data[2][2];  m23=data[2][3];
		m30=data[3][0];  m31=data[3][1];  m32=data[3][2];  m33=data[3][3];
	}
	
	/**
	 * return a two dimensional float array containing this Matrix
	 */
	public float[][] exportToArray()
	{
		float data[][] = new float[4][4];
		data[0][0]=m00;  data[0][1]=m01;  data[0][2]=m02;  data[0][3]=m03;
		data[1][0]=m10;  data[1][1]=m11;  data[1][2]=m12;  data[1][3]=m13;
		data[2][0]=m20;  data[2][1]=m21;  data[2][2]=m22;  data[2][3]=m23;
		data[3][0]=m30;  data[3][1]=m31;  data[3][2]=m32;  data[3][3]=m33;
		return data;
	}
	
	///////////////////////////////////////////////////////////
	// Factory Methods
	///////////////////////////////////////////////////////////
	
	/**
	 * create a Matrix shifted by the given amounts
	 */
	public static GL_Matrix translateMatrix(float dx, float dy, float dz)
	{
		GL_Matrix m = new GL_Matrix();
		m.m03=dx;
		m.m13=dy;
		m.m23=dz;
		return m;
	}
	
	/**
	 * create a Matrix to change scale
	 */
	public static GL_Matrix scaleMatrix(float dx, float dy, float dz)
	{
		GL_Matrix m=new GL_Matrix();
		m.m00=dx;
		m.m11=dy;
		m.m22=dz;
		return m;
	}
	
	/**
	 * create a Matrix to scale all axes equally
	 */
	public static GL_Matrix scaleMatrix(float d)
	{
		return GL_Matrix.scaleMatrix(d,d,d);
	}
	
	/**
	 * create a rotation matrix
	 */
	public static GL_Matrix rotateMatrix(float dx, float dy, float dz)
	{
		GL_Matrix out=new GL_Matrix();
		float SIN;
		float COS;
		
		if (dx!=0)
		{
			GL_Matrix m =new GL_Matrix();
			SIN = (float)Math.sin(dx);
			COS = (float)Math.cos(dx);
			m.m11=COS;
			m.m12=SIN;
			m.m21=-SIN;
			m.m22=COS;
			out.transform(m);
		}
		if (dy!=0)
		{
			GL_Matrix m =new GL_Matrix();
			SIN = (float)Math.sin(dy);
			COS = (float)Math.cos(dy);
			m.m00=COS;
			m.m02=SIN;
			m.m20=-SIN;
			m.m22=COS;
			out.transform(m);
		}
		if (dz!=0)
		{
			GL_Matrix m =new GL_Matrix();
			SIN = (float)Math.sin(dz);
			COS = (float)Math.cos(dz);
			m.m00=COS;
			m.m01=SIN;
			m.m10=-SIN;
			m.m11=COS;
			out.transform(m);
		}
		return out;
	}
	
	///////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////



    public void translate(float dx, float dy, float dz)
	{
		transform(translateMatrix(dx,dy,dz));
	}
	
	public void scale(float dx, float dy, float dz)
	{
		transform(scaleMatrix(dx,dy,dz));
	}
	
	public void scale(float d)
	{
		transform(scaleMatrix(d));
	}
	
	public void rotate(float dx, float dy, float dz)
	{
		transform(rotateMatrix(dx,dy,dz));
	}
	
	public void scaleSelf(float dx, float dy, float dz)
	{
		preTransform(scaleMatrix(dx,dy,dz));
	}
	
	public void scaleSelf(float d)
	{
		preTransform(scaleMatrix(d));
	}
	
	public void rotateSelf(float dx, float dy, float dz)
	{
		preTransform(rotateMatrix(dx,dy,dz));
	}
	
	/**
	 * reset to the identity matrix
	 */
	public void reset()
	{
		m00=1; m01=0; m02=0; m03=0;
		m10=0; m11=1; m12=0; m13=0;
		m20=0; m21=0; m22=1; m23=0;
		m30=0; m31=0; m32=0; m33=1;
	}
	
	/**
	 *  Transform the given vector using this matrix. Return the
	 *  transformed vector (the original vector is not modified).
	 *
	 *  @param v   GL_Vector to be transformed
	 *  @return    the transformed GL_Vector
	 */
	public GL_Vector transform(GL_Vector v)
	{
		if (v != null) {
			float newx, newy, newz;
			newx = v.x*m00 + v.y*m01 + v.z*m02+ m03;
			newy = v.x*m10 + v.y*m11 + v.z*m12+ m13;
			newz = v.x*m20 + v.y*m21 + v.z*m22+ m23;
			return new GL_Vector(newx,newy,newz);
		}
		return null;
	}
	
	/**
	 * transforms this matrix by matrix n from left (this=n x this)
	 */
	public void transform(GL_Matrix n)
	{
		GL_Matrix m = this.copyClone();
		
		m00 = n.m00*m.m00 + n.m01*m.m10 + n.m02*m.m20;
		m01 = n.m00*m.m01 + n.m01*m.m11 + n.m02*m.m21;
		m02 = n.m00*m.m02 + n.m01*m.m12 + n.m02*m.m22;
		m03 = n.m00*m.m03 + n.m01*m.m13 + n.m02*m.m23 + n.m03;
		m10 = n.m10*m.m00 + n.m11*m.m10 + n.m12*m.m20;
		m11 = n.m10*m.m01 + n.m11*m.m11 + n.m12*m.m21;
		m12 = n.m10*m.m02 + n.m11*m.m12 + n.m12*m.m22;
		m13 = n.m10*m.m03 + n.m11*m.m13 + n.m12*m.m23 + n.m13;
		m20 = n.m20*m.m00 + n.m21*m.m10 + n.m22*m.m20;
		m21 = n.m20*m.m01 + n.m21*m.m11 + n.m22*m.m21;
		m22 = n.m20*m.m02 + n.m21*m.m12 + n.m22*m.m22;
		m23 = n.m20*m.m03 + n.m21*m.m13 + n.m22*m.m23 + n.m23;
	}
	
	/**
	 * transforms this matrix by matrix n from right (this=this x n)
	 */
	public void preTransform(GL_Matrix n)
	{
		GL_Matrix m=this.copyClone();
		
		m00 = m.m00*n.m00 + m.m01*n.m10 + m.m02*n.m20;
		m01 = m.m00*n.m01 + m.m01*n.m11 + m.m02*n.m21;
		m02 = m.m00*n.m02 + m.m01*n.m12 + m.m02*n.m22;
		m03 = m.m00*n.m03 + m.m01*n.m13 + m.m02*n.m23 + m.m03;
		m10 = m.m10*n.m00 + m.m11*n.m10 + m.m12*n.m20;
		m11 = m.m10*n.m01 + m.m11*n.m11 + m.m12*n.m21;
		m12 = m.m10*n.m02 + m.m11*n.m12 + m.m12*n.m22;
		m13 = m.m10*n.m03 + m.m11*n.m13 + m.m12*n.m23 + m.m13;
		m20 = m.m20*n.m00 + m.m21*n.m10 + m.m22*n.m20;
		m21 = m.m20*n.m01 + m.m21*n.m11 + m.m22*n.m21;
		m22 = m.m20*n.m02 + m.m21*n.m12 + m.m22*n.m22;
		m23 = m.m20*n.m03 + m.m21*n.m13 + m.m22*n.m23 + m.m23;
	}
	
	/**
	 * Multiply the two matrices.  Return m1 x m2
	 */
	public static GL_Matrix multiply(GL_Matrix m1, GL_Matrix m2)
	{
		GL_Matrix m = new GL_Matrix();
		
		m.m00 = m1.m00*m2.m00 + m1.m01*m2.m10 + m1.m02*m2.m20 + m1.m03*m2.m30;
		m.m01 = m1.m00*m2.m01 + m1.m01*m2.m11 + m1.m02*m2.m21   + m1.m03*m2.m31;
		m.m02 = m1.m00*m2.m02 + m1.m01*m2.m12 + m1.m02*m2.m22   + m1.m03*m2.m32;
		m.m03 = m1.m00*m2.m03 + m1.m01*m2.m13 + m1.m02*m2.m23 + m1.m03*m2.m33;

		m.m10 = m1.m10*m2.m00 + m1.m11*m2.m10 + m1.m12*m2.m20   + m1.m13*m2.m30;
		m.m11 = m1.m10*m2.m01 + m1.m11*m2.m11 + m1.m12*m2.m21   + m1.m13*m2.m31;;
		m.m12 = m1.m10*m2.m02 + m1.m11*m2.m12 + m1.m12*m2.m22    + m1.m13*m2.m32;;
		m.m13 = m1.m10*m2.m03 + m1.m11*m2.m13 + m1.m12*m2.m23 + m1.m13*m2.m33;

		m.m20 = m1.m20*m2.m00 + m1.m21*m2.m10 + m1.m22*m2.m20   + m1.m23*m2.m30;
		m.m21 = m1.m20*m2.m01 + m1.m21*m2.m11 + m1.m22*m2.m21   + m1.m23*m2.m31;;
		m.m22 = m1.m20*m2.m02 + m1.m21*m2.m12 + m1.m22*m2.m22        + m1.m23*m2.m32;;
		m.m23 = m1.m20*m2.m03 + m1.m21*m2.m13 + m1.m22*m2.m23 + m1.m23*m2.m33;

        m.m30 = m1.m30*m2.m00 + m1.m31*m2.m10 + m1.m32*m2.m20   + m1.m33*m2.m30;
        m.m31 = m1.m30*m2.m01 + m1.m31*m2.m11 + m1.m32*m2.m21   + m1.m33*m2.m31;;
        m.m32 = m1.m30*m2.m02 + m1.m31*m2.m12 + m1.m32*m2.m22        + m1.m33*m2.m32;;
        m.m33 = m1.m30*m2.m03 + m1.m31*m2.m13 + m1.m32*m2.m23 + m1.m33*m2.m33;




        return m;
	}
	/**
	 * Multiply the two matrices.  Return m1 x m2
	 */
	public static GL_Vector multiply(GL_Matrix m1, GL_Vector vector)
	{
		GL_Vector newVector = new GL_Vector();
		newVector.x=  m1.m00*vector.x + m1.m01*vector.y + m1.m02*vector.z+ m1.m03;
		newVector.y=  m1.m10*vector.x + m1.m11*vector.y + m1.m12*vector.z+ m1.m13;
		newVector.z=  m1.m20*vector.x + m1.m21*vector.y + m1.m22*vector.z+ m1.m23;
		float w=  m1.m30*vector.x + m1.m31*vector.x + m1.m32*vector.x+ m1.m33;
		newVector.x=newVector.x/w;
		newVector.y=newVector.y/w;
		newVector.z=newVector.z/w;

		return newVector;
	}

    /**
     * Multiply the two matrices.  Return m1 x m2
     */
    public static Point4f multiply(GL_Matrix m1, Point4f vector)
    {
        Point4f newVector = new Point4f();
        newVector.x=  m1.m00*vector.x + m1.m01*vector.y + m1.m02*vector.z+ vector.w*m1.m03;
        newVector.y=  m1.m10*vector.x + m1.m11*vector.y + m1.m12*vector.z+ m1.m13*vector.w;
        newVector.z=  m1.m20*vector.x + m1.m21*vector.y + m1.m22*vector.z+ m1.m23*vector.w;
        newVector.w=  m1.m30*vector.x + m1.m31*vector.y + m1.m32*vector.z+ m1.m33*vector.w;


        return newVector;
    }

	/**
	 * Multiply the two matrices.  Return m1 x m2
	 */
	public static GL_Vector multiply(GL_Vector vector,GL_Matrix m1 )
	{
		GL_Vector newVector = new GL_Vector();
		newVector.x=  m1.m00*vector.x + m1.m10*vector.y + m1.m20*vector.z+ m1.m30;
		newVector.y= m1.m01*vector.x + m1.m11*vector.y + m1.m21*vector.z+ m1.m31;
		newVector.z=  m1.m02*vector.x + m1.m12*vector.y + m1.m22*vector.z+ m1.m32;
		//newVector.w=  m1.m30*vector.x + m1.m31*vector.x + m1.m32*vector.x+ m1.m33;


		return newVector;
	}

    /**
     * Multiply the two matrices.  Return m1 x m2
     */
    public static Point4f multiply(Point4f vector,GL_Matrix m1 )
    {
        Point4f point4f = new Point4f();
        point4f.x=  m1.m00*vector.x + m1.m10*vector.y + m1.m20*vector.z+vector.w* m1.m30;
        point4f.y= m1.m01*vector.x + m1.m11*vector.y + m1.m21*vector.z+ vector.w*m1.m31;
        point4f.z=  m1.m02*vector.x + m1.m12*vector.y + m1.m22*vector.z+ vector.w*m1.m32;
        point4f.w=  m1.m03*vector.x + m1.m13*vector.y + m1.m23*vector.z+ vector.w*m1.m33;
        //newVector.w=  m1.m30*vector.x + m1.m31*vector.x + m1.m32*vector.x+ m1.m33;


        return point4f;
    }

	/**
	 * return a string representation of this matrix
	 */
	public String toString()
	{
		StringBuffer out=new StringBuffer("<Matrix: \r\n");
		out.append(m00+","+m01+","+m02+","+m03+",\r\n");
		out.append(m10+","+m11+","+m12+","+m13+",\r\n");
		out.append(m20+","+m21+","+m22+","+m23+",\r\n");
		out.append(m30+","+m31+","+m32+","+m33+">\r\n");
		return out.toString();
	}
	
	/**
	 * return a copy of this matrix
	 */
	public GL_Matrix copyClone()
	{
		GL_Matrix m=new GL_Matrix();
		m.m00=m00;  m.m01=m01;  m.m02=m02;  m.m03=m03;
		m.m10=m10;  m.m11=m11;  m.m12=m12;  m.m13=m13;
		m.m20=m20;  m.m21=m21;  m.m22=m22;  m.m23=m23;
		m.m30=m30;  m.m31=m31;  m.m32=m32;  m.m33=m33;
		return m;
	}
	public float determinant() {
		float f = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
		f -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
		f += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
		f -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
		return f;
	}
	private static float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22) {
		return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20);
	}
	public static GL_Matrix invert(GL_Matrix src, GL_Matrix dest) {
		float determinant = src.determinant();
		if(determinant != 0.0F) {
			if(dest == null) {
				dest = new GL_Matrix();
			}

			float determinant_inv = 1.0F / determinant;
			float t00 = determinant3x3(src.m11, src.m12, src.m13, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
			float t01 = -determinant3x3(src.m10, src.m12, src.m13, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
			float t02 = determinant3x3(src.m10, src.m11, src.m13, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
			float t03 = -determinant3x3(src.m10, src.m11, src.m12, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
			float t10 = -determinant3x3(src.m01, src.m02, src.m03, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
			float t11 = determinant3x3(src.m00, src.m02, src.m03, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
			float t12 = -determinant3x3(src.m00, src.m01, src.m03, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
			float t13 = determinant3x3(src.m00, src.m01, src.m02, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
			float t20 = determinant3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m31, src.m32, src.m33);
			float t21 = -determinant3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m30, src.m32, src.m33);
			float t22 = determinant3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m30, src.m31, src.m33);
			float t23 = -determinant3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m30, src.m31, src.m32);
			float t30 = -determinant3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m21, src.m22, src.m23);
			float t31 = determinant3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m20, src.m22, src.m23);
			float t32 = -determinant3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m20, src.m21, src.m23);
			float t33 = determinant3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m20, src.m21, src.m22);
			dest.m00 = t00 * determinant_inv;
			dest.m11 = t11 * determinant_inv;
			dest.m22 = t22 * determinant_inv;
			dest.m33 = t33 * determinant_inv;
			dest.m01 = t10 * determinant_inv;
			dest.m10 = t01 * determinant_inv;
			dest.m20 = t02 * determinant_inv;
			dest.m02 = t20 * determinant_inv;
			dest.m12 = t21 * determinant_inv;
			dest.m21 = t12 * determinant_inv;
			dest.m03 = t30 * determinant_inv;
			dest.m30 = t03 * determinant_inv;
			dest.m13 = t31 * determinant_inv;
			dest.m31 = t13 * determinant_inv;
			dest.m32 = t23 * determinant_inv;
			dest.m23 = t32 * determinant_inv;
			return dest;
		} else {
			return null;
		}
	}
	public GL_Matrix invert() {
		return invert(this, this);
	}
	/**
	 * return the inverse of this matrix
	 */
	public GL_Matrix inverse()
	{
		GL_Matrix m = new GL_Matrix();
		
		float q1 = m12;  float q6 = m10*m01;  float q7 = m10*m21;  float q8 = m02;
		float q13 = m20*m01;  float q14 = m20*m11;  float q21 = m02*m21;  float q22 = m03*m21;
		float q25 = m01*m12;  float q26 = m01*m13;  float q27 = m02*m11;  float q28 = m03*m11;
		float q29 = m10*m22;  float q30 = m10*m23;  float q31 = m20*m12;  float q32 = m20*m13;
		float q35 = m00*m22;  float q36 = m00*m23;  float q37 = m20*m02;  float q38 = m20*m03;
		float q41 = m00*m12;  float q42 = m00*m13;  float q43 = m10*m02;  float q44 = m10*m03;
		float q45 = m00*m11;  float q48 = m00*m21;
		float q49 = q45*m22-q48*q1-q6*m22+q7*q8;
		float q50 = q13*q1-q14*q8;
		float q51 = 1/(q49+q50);
		
		m.m00 = (m11*m22*m33-m11*m23*m32-m21*m12*m33+m21*m13*m32+m31*m12*m23-m31*m13*m22)*q51;
		m.m01 = -(m01*m22*m33-m01*m23*m32-q21*m33+q22*m32)*q51;
		m.m02 = (q25*m33-q26*m32-q27*m33+q28*m32)*q51;
		m.m03 = -(q25*m23-q26*m22-q27*m23+q28*m22+q21*m13-q22*m12)*q51;
		m.m10 = -(q29*m33-q30*m32-q31*m33+q32*m32)*q51;
		m.m11 = (q35*m33-q36*m32-q37*m33+q38*m32)*q51;
		m.m12 = -(q41*m33-q42*m32-q43*m33+q44*m32)*q51;
		m.m13 = (q41*m23-q42*m22-q43*m23+q44*m22+q37*m13-q38*m12)*q51;
		m.m20 = (q7*m33-q30*m31-q14*m33+q32*m31)*q51;
		m.m21 = -(q48*m33-q36*m31-q13*m33+q38*m31)*q51;
		m.m22 = (q45*m33-q42*m31-q6*m33+q44*m31)*q51;
		m.m23 = -(q45*m23-q42*m21-q6*m23+q44*m21+q13*m13-q38*m11)*q51;
		
		return m;
	}
	public static GL_Matrix RotationMatrix(double angle, GL_Vector axis)
{

	GL_Matrix src = new GL_Matrix();
	GL_Matrix dest = new GL_Matrix();
	float c = (float)Math.cos((double)angle);
	float s = (float)Math.sin((double)angle);
	float oneminusc = 1.0F - c;
	float xy = axis.x * axis.y;
	float yz = axis.y * axis.z;
	float xz = axis.x * axis.z;
	float xs = axis.x * s;
	float ys = axis.y * s;
	float zs = axis.z * s;
	float f00 = axis.x * axis.x * oneminusc + c;
	float f01 = xy * oneminusc + zs;
	float f02 = xz * oneminusc - ys;
	float f10 = xy * oneminusc - zs;
	float f11 = axis.y * axis.y * oneminusc + c;
	float f12 = yz * oneminusc + xs;
	float f20 = xz * oneminusc + ys;
	float f21 = yz * oneminusc - xs;
	float f22 = axis.z * axis.z * oneminusc + c;
	float t00 = src.m00 * f00 + src.m10 * f01 + src.m20 * f02;
	float t01 = src.m01 * f00 + src.m11 * f01 + src.m21 * f02;
	float t02 = src.m02 * f00 + src.m12 * f01 + src.m22 * f02;
	float t03 = src.m03 * f00 + src.m13 * f01 + src.m23 * f02;
	float t10 = src.m00 * f10 + src.m10 * f11 + src.m20 * f12;
	float t11 = src.m01 * f10 + src.m11 * f11 + src.m21 * f12;
	float t12 = src.m02 * f10 + src.m12 * f11 + src.m22 * f12;
	float t13 = src.m03 * f10 + src.m13 * f11 + src.m23 * f12;
	dest.m20 = src.m00 * f20 + src.m10 * f21 + src.m20 * f22;
	dest.m21 = src.m01 * f20 + src.m11 * f21 + src.m21 * f22;
	dest.m22 = src.m02 * f20 + src.m12 * f21 + src.m22 * f22;
	dest.m23 = src.m03 * f20 + src.m13 * f21 + src.m23 * f22;
	dest.m00 = t00;
	dest.m01 = t01;
	dest.m02 = t02;
	dest.m03 = t03;
	dest.m10 = t10;
	dest.m11 = t11;
	dest.m12 = t12;
	dest.m13 = t13;
	return dest;

	/*axis.normalize();
	GL_Matrix rotatinMatrix = new GL_Matrix();



	rotatinMatrix.m00 =(float) (Math.cos(angle) + axis.x*axis.x * (1 - Math.cos(angle)));
	rotatinMatrix.m01 =(float) (axis.x * axis.y * (1 - Math.cos(angle) - axis.z * Math.sin(angle)));
	rotatinMatrix.m02 =(float) ( axis.y * Math.sin(angle) + axis.x * axis.z * (1 - Math.cos(angle)));

	rotatinMatrix.m10 = (float) (axis.z * Math.sin(angle) + axis.x * axis.y * (1 - Math.cos(angle)));
	rotatinMatrix.m11 = (float) (Math.cos(angle) + axis.y * axis.y * (1 - Math.cos(angle)));
	rotatinMatrix.m12 = (float) (-axis.x * Math.sin(angle) + axis.y * axis.z * (1 - Math.cos(angle)));

	rotatinMatrix.m20 = (float) (-axis.y * Math.sin(angle) + axis.x * axis.z * (1 - Math.cos(angle)));
	rotatinMatrix.m21 = (float) (axis.x * Math.sin(angle) + axis.y * axis.z * (1 - Math.cos(angle)));
	rotatinMatrix.m22 =(float) ( Math.cos(angle) + axis.z * axis.z * (1 - Math.cos(angle)));*/

	//return rotatinMatrix;
}
	/**
	 * vCreate the billboard matrix: a rotation matrix created from an arbitrary set
	 * of axis.  Store those axis values in the first 3 columns of the matrix.  Col
	 * 1 is the X axis, col 2 is the Y axis, and col 3 is the Z axis.  We are
	 * rotating right into X, up into Y, and look into Z.  The rotation matrix
	 * created from the rows will translate the arbitrary axis set to the global
	 * vaxis set.  Lastly, OpenGl stores the matrices by columns, so enter the data
	 * into the array columns first.
	 *
	 * pos: position of billboard
	 * right, up, look: orientation of billboard x,y,z axes
	 */
	public static void createBillboardMatrix(FloatBuffer matrix, GL_Vector right, GL_Vector up, GL_Vector look, GL_Vector pos)
	{
		matrix.put(0,  right.x);
		matrix.put(1,  right.y);
		matrix.put(2,  right.z);
		matrix.put(3,  0);
		
		matrix.put(4,  up.x);
		matrix.put(5,  up.y);
		matrix.put(6,  up.z);
		matrix.put(7,  0);
		
		matrix.put(8,  look.x);
		matrix.put(9,  look.y);
		matrix.put(10, look.z);
		matrix.put(11, 0);
		
		// Add the translation in as well.
		matrix.put(12, pos.x);
		matrix.put(13, pos.y);
		matrix.put(14, pos.z);
		matrix.put(15, 1);
	}

    /**
     * 正交投影
     */
    public static GL_Matrix ortho(float left, float right,float bottom,float top ,float near ,float far)
    {
        GL_Matrix m = new GL_Matrix();
        m.m00=2/(right-left);
        m.m01=0;
        m.m02=0;
        m.m03=0;

        m.m10=0;
        m.m11=2/(top-bottom);
        m.m12=0;
        m.m13=0;

        m.m20=0;
        m.m21=0;
        m.m22=2/(near-far);
        m.m23=(far+near)/(near-far);

        m.m30=0;
        m.m31=0;
        m.m32=0;
        m.m33=1;

        return m;
    }
    /**
     * 透视投影
     */
    public static GL_Matrix perspective(float fov, float aspect,float zn,float zf )
    {fov=(float)( Constants.PI1*fov);
        GL_Matrix m = new GL_Matrix();
       m.m00=(float)(1/(Math.tan(fov*0.5f)*aspect));
        m.m11=(float)(1/(Math.tan(fov*0.5f)));
        m.m22=(float)(zf/(zf-zn));
        m.m23=1.0f;
		m.m33=0f;
        m.m32=(float)(zn*zf/(zn-zf));
        return m;
    }

    public static GL_Matrix
         LookAt (GL_Vector camera,GL_Vector direction1){
        //GL_Vector direction =  GL_Vector.sub(camera,target).normalize();

        GL_Vector direction = new GL_Vector( -direction1.x,-direction1.y,-direction1.z);

        GL_Vector up = new GL_Vector( 0,1,0);
        GL_Vector cameraRight = GL_Vector.crossProduct(up,direction).normalize();
        up = GL_Vector.crossProduct(direction,cameraRight).normalize();
        GL_Matrix left = new GL_Matrix();
        left.m00 = cameraRight.x;
        left.m01=cameraRight.y;
        left.m02=cameraRight.z;

        left.m10 = up.x;
        left.m11=up.y;
        left.m12=up.z;

        left.m20 = direction.x;
        left.m21=direction.y;
        left.m22=direction.z;

        GL_Matrix right = new GL_Matrix();
        right.m03 = -camera.x;

        right.m13 = -camera.y;
        right.m23 = -camera.z;

        return GL_Matrix.multiply(left,right);
    }
	public static GL_Matrix perspective3(float fov, float aspect,float zn,float zf )
	{

        fov=(float)( Constants.PI1*fov);
		GL_Matrix m = new GL_Matrix();
		m.m00=(float)(1/(Math.tan(fov*0.5f)*aspect));
		m.m11=(float)(1/(Math.tan(fov*0.5f)));
		m.m22=(float)(-zn-zf)/(zf-zn);
		m.m32=-1.0f;

		m.m23=(float)(-2*zn*zf/(zf-zn));
		m.m33=0f;
		return m;
	}
	public static GL_Matrix  perspective2(float fov, float aspect,float zn,float zf )
	{GL_Matrix m = new GL_Matrix();
		 float ar = aspect;
		 float zNear = zn;
		 float zFar = zf;
		 float zRange = zn - zf;
		double radi=  Constants.PI1*(fov / 2.0);
		 float tanHalfFOV =(float)( Math.tan((float)radi));

		m.m00 = 1.0f / (tanHalfFOV * ar);
		m.m01 = 0.0f;
		m.m02 = 0.0f;
		m.m03 = 0.0f;

		m.m10 = 0.0f;
		m.m11 = 1.0f / tanHalfFOV;
		m.m12 = 0.0f;
		m.m13 = 0.0f;

		m.m20 = 0.0f;
		m.m21 = 0.0f;
		m.m22 = (-zNear - zFar) / zRange;
		m.m23= 2.0f * zFar * zNear / zRange;

		m.m30= 0.0f;
		m.m31 = 0.0f;
		m.m32 = 1.0f;
		m.m33 = 0.0f;
		return m;
	}
    public static GL_Matrix  perspective4   (float fov, float aspect,float zn,float zf )
    {GL_Matrix m = new GL_Matrix();
        float ar = aspect;
        float zNear = zn;
        float zFar = zf;
        float zRange = zn - zf;
        double radi=  Constants.PI1*(fov / 2.0);
        float tanHalfFOV =(float)( Math.tan((float)radi));

        m.m00 = 1.0f / (tanHalfFOV * ar);
        m.m01 = 0.0f;
        m.m02 = 0.0f;
        m.m03 = 0.0f;

        m.m10 = 0.0f;
        m.m11 = 1.0f / tanHalfFOV;
        m.m12 = 0.0f;
        m.m13 = 0.0f;

        m.m20 = 0.0f;
        m.m21 = 0.0f;
        m.m22 = (-zNear - zFar) / zRange;
        m.m23= 2.0f * zFar * zNear / zRange;

        m.m30= 0.0f;
        m.m31 = 0.0f;
        m.m32 = 1.0f;
        m.m33 = 0.0f;
        return m;
    }

    /**
     * 正交投影
     */
    public static GL_Matrix perspective(float left, float right,float bottom,float top ,float near ,float far)
    {
        GL_Matrix m = new GL_Matrix();
        m.m00=2/(right-left);
        m.m01=0;
        m.m02=0;
        m.m03=-(right+left)/(right-left);

        m.m10=0;
        m.m11=2/(top-bottom);
        m.m12=0;
        m.m13=-(top+bottom)/(top-bottom);

        m.m20=0;
        m.m21=0;
        m.m22=-2/(far-near);
        m.m23=-(far+near)/(far-near);

        m.m30=0;
        m.m31=0;
        m.m32=0;
        m.m33=1;

        return m;
    }

    public FloatBuffer toReverseFloatBuffer(){
       FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        float[][] arr= this.exportToArray();

        for(int j = 0;j<4;j++) {//数据居然要从上往下算第一列 再第二列传数据
            for (int i = 0; i < 4; i++) {
                matrixBuffer.put(arr[i][j]);
            }
        }
        matrixBuffer.rewind();
        return matrixBuffer;



    }

    public void fillFloatBuffer(FloatBuffer matrixBuffer){
        matrixBuffer.rewind();
        matrixBuffer.put(m00);
        matrixBuffer.put(m10);
        matrixBuffer.put(m20);
        matrixBuffer.put(m30);
        matrixBuffer.put(m01);
        matrixBuffer.put(m11);
        matrixBuffer.put(m21);
        matrixBuffer.put(m31);
        matrixBuffer.put(m02);
        matrixBuffer.put(m12);
        matrixBuffer.put(m22);
        matrixBuffer.put(m32);
        matrixBuffer.put(m03);
        matrixBuffer.put(m13);
        matrixBuffer.put(m23);
        matrixBuffer.put(m33);

        matrixBuffer.rewind();

    }
	public FloatBuffer toFloatBuffer(){
		FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
		float[][] arr= this.exportToArray();
       /* matrixBuffer.put(1).put(0).put(0).put(0)
                .put(0).put(1).put(0).put(0).
                put(0).put(0).put(1).put(0).
                put(0).put(0).put(0).put(1);*/
		for(int i = 0;i<4;i++) {//数据居然要从上往下算第一列 再第二列传数据
			for (int j = 0; j < 4; j++) {
				matrixBuffer.put(arr[j][i]);
			}
		}
		matrixBuffer.flip();
        //System.out.println();
		return matrixBuffer;



	}

	public static void main(String args[]){

		GL_Vector gl_vector=new GL_Vector(1,0,0);
		GL_Matrix transform = GL_Matrix.rotateMatrix(0,(float)(90.0*3.14/180.0),0);

		gl_vector =transform.transform(gl_vector);
		GL_Matrix model= GL_Matrix.rotateMatrix((float)(45.0*3.14/180.0),0,0);

		GL_Matrix view= GL_Matrix.translateMatrix(0,0,3);
		GL_Matrix projection= GL_Matrix.perspective3(45, 600 / 600, 1f, 1000.0f);
		GL_Matrix finalMatrix =  GL_Matrix.multiply(GL_Matrix.multiply(projection,view),model);
		// GL_Vector vector = finalMatrix.multiply(projection,new GL_Vector(0f,4f,-4f));


        float z=-13f;
        float multi=1;
        float VerticesArray[]= {0.5f*multi, 0.5f*multi,z, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // Top Right
                0.5f*multi, -0.5f*multi, z, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // Bottom Right
                -0.5f*multi, -0.5f*multi,z, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // Bottom Left
                -0.5f*multi, 0.5f*multi, z, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Top Left
        };
        //
        float n =1f;


        for(int i=0;i<4;i++){
            Point4f eyeVector =new Point4f(VerticesArray[0+i*8],VerticesArray[1+i*8],VerticesArray[2+i*8],1);
            //Point4f
           // GL_Vector vector2 =  GL_Matrix.multiply(model,neVector);
           // GL_Vector vector3 =  GL_Matrix.multiply(view,vector2);
            //Point4f vector3=neVector;
            Point4f clipVector =  GL_Matrix.multiply(projection,eyeVector);
            Point4f nVector= new Point4f();
            nVector.x=-1*clipVector.x/eyeVector.z;
            nVector.y=-1*clipVector.y/eyeVector.z;
            nVector.z=-1*clipVector.z/eyeVector.z;
            nVector.w=-1*clipVector.w/ eyeVector.z; //eyeVector.z;

            float xp =-1*n*eyeVector.x/eyeVector.z;
            float yp =-1*n*eyeVector.y/eyeVector.z;
            //vector.z=vector.z/vector.w;
            //vector.w=vector.w/vector.w;
        /*    GL_Vector vector =  GL_Matrix.multiply(neVector,projection);
            vector.x=vector.x/neVector.z;
            vector.y=vector.y/neVector.z;
            vector.z=-1*vector.z/neVector.z;
               vector =  GL_Matrix.multiply(model,vector);
             vector =  GL_Matrix.multiply(view,vector);*/


            System.out.print(eyeVector+" ====> "+clipVector+"=====>"+nVector+"\n");



        }

	}

}