package glmodel;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import core.log.LogUtil;
import glapp.GLApp;

import java.nio.FloatBuffer;

/**
 * A 3D vector, with functions to perform common vector math operations.
 */
public class GL_Vector
{
	//z方向的向量
	public static final GL_Vector zDir = new GL_Vector( 0,0,1);
	@JSONField(name="x")
	public float x=0;
	@JSONField(name="y")
	public float y=0;
	@JSONField(name="z")
	public float z=0;
	@JSONField(name="x")
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}
	@JSONField(name="y")
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	@JSONField(name="z")
	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Create a 0,0,0 vector 
	 */
	public GL_Vector()
	{
	}
	
	/**
	 * Create a vector with the given xyz values
	 */
	public GL_Vector(float xpos, float ypos, float zpos)
	{
		x = xpos;
		y = ypos;
		z = zpos;
	}
	
	/**
	 * Create a vector from the given float[3] xyz values
	 */
	public GL_Vector (float[] float3)
	{
		x = float3[0];
		y = float3[1];
		z = float3[2];
	}
	
	/**
	 * Create a vector that duplicates the given vector
	 */
	public GL_Vector(GL_Vector v)
	{
		x = v.x;
		y = v.y;
		z = v.z;
	}
	public void copy(GL_Vector v){
        x=v.x;
        y=v.y;
        z=v.z;
    }
    public void set(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
	/**
	 * Create a vector from point1 to point2
	 */
	public GL_Vector(GL_Vector point1, GL_Vector point2)
	{
		x = point1.x - point2.x;
		y = point1.y - point2.y;
		z = point1.z - point2.z;
	}
	
	//========================================================================
	// Functions that operate on the vector (change the value of this vector)
	// These functions return "this", so can be chained together:
	//        GL_Vector a = new GLVector(b).mult(c).normalize()
	//========================================================================
	
	/**
	 * Add a vector to this vector
	 */
	public GL_Vector add(GL_Vector v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	/**
	 * Subtract vector from this vector
	 */
	public GL_Vector sub(GL_Vector v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	/**
	 * Multiply this vector by another vector
	 */
	public GL_Vector mult(GL_Vector v)
	{
		x *= v.x;
		y *= v.y;
		z *= v.z;
		return this;
	}
	
	/**
	 * Divide this vector by another vector
	 */
	public GL_Vector div(GL_Vector v)
	{
		x /= v.x;
		y /= v.y;
		z /= v.z;
		return this;
	}
	
	/**
	 * Add a value to this vector
	 */
	public GL_Vector add(float n)
	{
		x += n;
		y += n;
		z += n;
		return this;
	}
	
	/**
	 * Subtract a value from this vector
	 */
	public GL_Vector sub(float n)
	{
		x -= n;
		y -= n;
		z -= n;
		return this;
	}
	
	/**
	 * Multiply vector by a value
	 */
	public GL_Vector mult(float n)
	{
		x *= n;
		y *= n;
		z *= n;
		return this;
	}
	
	/**
	 * Divide vector by a value
	 */
	public GL_Vector div(float n)
	{
		x /= n;
		y /= n;
		z /= n;
		return this;
	}
	
	/**
	 * Normalize the vector (make its length 0).
	 */
	public GL_Vector normalize()
	{
		float len = length();
		if (len==0) return this;
		float invlen = 1f/len;
		x *= invlen;
		y *= invlen;
		z *= invlen;
		return this;
	}
	
	/**
	 * Reverse the vector
	 */
	public GL_Vector reverse()
	{
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	/**
	 * Return the length of the vector.
	 */
	public float length()
	{
		return (float)Math.sqrt(x*x+y*y+z*z);
	}
	
	/**
	 * Return a string representation of the vector
	 */
	public String toString()
	{
		return new String ("<vector x="+x+" y="+y+" z="+z+">");
	}
	
	/**
	 * Return a copy of the vector
	 */
	public GL_Vector copyClone()
	{
		return new GL_Vector(x,y,z);
	}
	
	/**
	 * Return true if this vector has the same xyz values as the argument vector
	 */
	public boolean equals(GL_Vector v)
	{
		return (v.x==x && v.y==y && v.z==z);
	}
	
	//==================================================================
	// Functions that perform binary operations (add, subtract, multiply
	// two vectors and return answer in a new vector)
	//==================================================================
	
	/**
	 * Return a+b as a new vector
	 */
	public static GL_Vector add(GL_Vector a, GL_Vector b)
	{
		return new GL_Vector(a.x+b.x, a.y+b.y, a.z+b.z);
	}
	
	/**
	 * Return a-b as a new vector
	 */
	public static GL_Vector sub(GL_Vector a, GL_Vector b)
	{
		return new GL_Vector(a.x-b.x, a.y-b.y, a.z-b.z);
	}
	
	/**
	 * Return a*b as a new vector
	 */
	public static GL_Vector mult(GL_Vector a, GL_Vector b)
	{
		return new GL_Vector(a.x*b.x, a.y*b.y, a.z*b.z);
	}
	
	/**
	 * Return a/b as a new vector
	 */
	public static GL_Vector div(GL_Vector a, GL_Vector b)
	{
		return new GL_Vector(a.x/b.x, a.y/b.y, a.z/b.z);
	}
	
	/**
	 * Return the given vector multiplied by the given numeric value, as a new vector
	 */
	public static GL_Vector multiply(GL_Vector v, float r) {
		return new GL_Vector(v.x*r, v.y*r, v.z*r);
	}
	
	/**
	 * Return a new vector scaled by the given factor
	 */
	public static GL_Vector scale(GL_Vector a, float f)
	{
		return new GL_Vector(f*a.x,f*a.y,f*a.z);
	}
	
	/**
	 * Return the length of the given vector
	 */
	public static float length(GL_Vector a)
	{
		return (float)Math.sqrt(a.x*a.x+a.y*a.y+a.z*a.z);
	}
	
	/**
	 *  return the dot product of two vectors
	 */
	public static float dotProduct(GL_Vector u, GL_Vector v)
	{
		return u.x * v.x + u.y * v.y + u.z * v.z;
	}
	
	/**
	 * Return the normalized vector as a new vector
	 */
	public static GL_Vector normalize(GL_Vector v) {
		float len = v.length();
		if (len==0) return v;
		float invlen = 1f/len;
		return new GL_Vector(v.x*invlen, v.y*invlen, v.z*invlen);
	}
	
	/**
	 * Return the cross product of the two vectors, as a new vector.  The returned vector is 
	 * perpendicular to the plane created by a and b. 计算向量 a和b的垂直向量
	 */
	public static GL_Vector crossProduct(GL_Vector a, GL_Vector b)
	{
		return vectorProduct(a,b).normalize();
	}
	
	/**
	 * returns the normal vector of the plane defined by the a and b vectors
	 */
	public static GL_Vector getNormal(GL_Vector a, GL_Vector b)
	{
		return vectorProduct(a,b).normalize();
	}
	
	/**
	 * returns the normal vector from the three vectors
	 */ 
	public static GL_Vector getNormal(GL_Vector a, GL_Vector b, GL_Vector c)
	{
		return vectorProduct(a,b,c).normalize();
	}
	
	/**
	 * returns a x b
	 */
	public static GL_Vector vectorProduct(GL_Vector a, GL_Vector b)
	{
		return new GL_Vector(a.y*b.z-b.y*a.z, a.z*b.x-b.z*a.x, a.x*b.y-b.x*a.y);
	}
	
	/**
	 * returns (b-a) x (c-a)
	 */ 
	public static GL_Vector vectorProduct(GL_Vector a, GL_Vector b, GL_Vector c)
	{
		return vectorProduct(sub(b,a),sub(c,a));
	}
	
	/**
	 * returns the angle between 2 vectors
	 */
	public static float angle(GL_Vector a, GL_Vector b)
	{
		GL_Vector newA= new GL_Vector();
		newA.copy(a);
		GL_Vector newB= new GL_Vector();
		newB.copy(b);
		newA.normalize();
		newB.normalize();
		return (newA.x*newB.x+newA.y*newB.y+newA.z*newB.z);
		//return (a.x*b.x+a.y*b.y+a.z*b.z);
	}


	/*设a=（-1,3,2） b=（2,-2,2）θ为向量a、b的夹角
	|a|=根号（（-1）^2+3^2+2^2)=根号14
	同理|b|=根号12
	cosθ=（a•b）∕（|a||b|)=（-2-6+4）∕（根号14*根号12）=-（根号2∕根号21）
	所以θ=arccos（-（根号2∕根号21））*/

	public static float angle2(GL_Vector a, GL_Vector b)
	{
		GL_Vector mult =GL_Vector.mult(a,b);
		float result =(mult.x+mult.y+mult.z)/(GL_Vector.length(a)*GL_Vector.length(b));
		return (float)Math.acos(result);
		//return (a.x*b.x+a.y*b.y+a.z*b.z);
	}

	public static float updownAngle(GL_Vector ViewDir){
		double xy= Math.sqrt(ViewDir.x*ViewDir.x + ViewDir.z*ViewDir.z);
		double jiaojiao = Math.atan(ViewDir.y/xy);
		return (float)jiaojiao;
	}
	/**
	 * GL_Vector point 点相对起始坐标的坐标
	 * viewDir 方向
	 * @param point
	 * @param viewDir
     * @return
     */
	public static double chuizhijuli(GL_Vector point,GL_Vector viewDir) {
		GL_Vector ap = point;

		GL_Vector ab =viewDir;
		float angle =angle2(ap,ab);
		double length = Math.cos(Math.atan(angle))*GL_Vector.length(ap);

		GL_Vector norm= GL_Vector.multiply(ab.normalize(),(float)length);
		 length =GL_Vector.length(GL_Vector.sub(norm,ap));
		LogUtil.println("chuizhi juli "+length);

		return length;
	}


	public static void main(String args[]){
		GL_Vector a= new GL_Vector(0.5f,0.5f,0).normalize();
		GL_Vector b= new GL_Vector(0,1,0);
		float result = GL_Vector.angle(a,b);
		LogUtil.println(result+"");

		GL_Vector point =new GL_Vector(0,1,0);
		GL_Vector from =new GL_Vector(0,0,0);
		GL_Vector to =new GL_Vector(0.5f,0.5f,0);

		LogUtil.println(JSON.toJSONString(point));
		//LogUtil.println(chuizhijuli(point,from,to)+"");
	}
	/**
	 *  returns the angle between 2 vectors on the XZ plane.
	 *  angle is 0-360 where the 0/360 divide is directly in front of the A vector
	 *  Ie. when A is pointing directly at B, angle will be 0.
	 *  If B moves one degree to the right, angle will be 1,
	 *  If B moves one degree to the left, angle will be 360.
	 *
	 *  right side is 0-180, left side is 360-180
	 */
	public static float angleXZ(GL_Vector a, GL_Vector b)
	{
		a.normalize();
		b.normalize();
		return (float)((Math.atan2(a.x*b.z-b.x*a.z, a.x*b.x+a.z*b.z) + Math.PI) * GLApp.PIUNDER180);
	}

	public static float angleXZ2(GL_Vector a, GL_Vector b)
	{
		a.normalize();
		b.normalize();
		return (float)((Math.atan2(a.x*b.z-b.x*a.z, a.x*b.x+a.z*b.z) + Math.PI) );
	}
	
	/**
	 *  returns the angle between 2 vectors on the XY plane.
	 *  angle is 0-360 where the 0/360 divide is directly in front of the A vector
	 *  Ie. when A is pointing directly at B, angle will be 0.
	 *  If B moves one degree to the right, angle will be 1,
	 *  If B moves one degree to the left, angle will be 360.
	 *
	 *  right side is 0-180, left side is 360-180
	 */
	public static float angleXY(GL_Vector a, GL_Vector b)
	{
		a.normalize();
		b.normalize();
		return (float)((Math.atan2(a.x*b.y-b.x*a.y, a.x*b.x+a.y*b.y) + Math.PI) * GLApp.PIUNDER180);
	}
	
	/**
	 * return a vector rotated the given number of degrees around the Y axis  
	 */
	public static GL_Vector rotationVector(float degrees) {
		return new GL_Vector(
				(float)Math.sin(degrees * GLApp.PIOVER180),
				0,
				(float)Math.cos(degrees * GLApp.PIOVER180) );
	}
	
	/**
	 * copy all value from b to a 
	 */
	public static void rotationVector(GL_Vector  A , GL_Vector  B) {
		A.x=B.x;
		A.y=B.y;
		A.z=B.z;
	}
    public static GL_Vector rotateWithZ(GL_Vector  A, float degree){
        degree= degree* Constants.PI1;
        return new GL_Vector((float)(A.x*Math.cos(degree)-A.y*Math.sin(degree)),(float)(A.x*Math.sin(degree)+A.y*Math.cos(degree)),A.z);
    }

	public static GL_Vector multiplyWithoutY(GL_Vector v, float r) {
		return new GL_Vector(v.x*r, 0, v.z*r);
	}

	public static void glVertex3fv(GL_Vector point, FloatBuffer floatBuffer){
		floatBuffer.put(point.x).put(point.y).put(point.z);
	}
	public static void glVertex3fv4rect(GL_Vector p1,GL_Vector p2,GL_Vector p3,GL_Vector p4,GL_Matrix matrix,GL_Vector normal,float minx,float miny ,float maxx,float maxy, FloatBuffer floatBuffer){
		p1 =GL_Matrix.multiply(matrix,p1);

		p2 =GL_Matrix.multiply(matrix,p2);
		p3 =GL_Matrix.multiply(matrix,p3);
		p4 =GL_Matrix.multiply(matrix,p4);
		normal=GL_Matrix.multiply(matrix,normal);
		floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(minx).put(miny);
		floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(minx).put(maxy);
		floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(maxx).put(maxy);
		floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(minx).put(maxy);
	}
	public static void glVertex3fv4rect(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Matrix matrix, GL_Vector normal, TextureInfo ti, FloatBuffer floatBuffer){
		p1 =GL_Matrix.multiply(matrix,p1);

		p2 =GL_Matrix.multiply(matrix,p2);
		p3 =GL_Matrix.multiply(matrix,p3);
		p4 =GL_Matrix.multiply(matrix,p4);
		normal=GL_Matrix.multiply(matrix,normal);


		floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY);
		floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.minY);
		floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY);
		floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.maxY);
	}
	public static void glVertex3fv4triangle(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Matrix matrix, GL_Vector normal, TextureInfo ti, FloatBuffer floatBuffer,ShaderConfig config){
		//ti= TextureManager.getTextureInfo("mantle");
		int index = ShaderUtils.bindAndGetTextureIndex(config,ti.textureHandle);

		p1 =GL_Matrix.multiply(matrix,p1);

		p2 =GL_Matrix.multiply(matrix,p2);
		p3 =GL_Matrix.multiply(matrix,p3);
		p4 =GL_Matrix.multiply(matrix,p4);
		normal=GL_Matrix.multiply(matrix,normal);
		floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);
		floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.minY).put(0).put(index);
		floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);
		floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.maxY).put(0).put(index);
		floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);
		floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);
	}

	public static void glVertex3fv4triangleColor(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Matrix matrix, GL_Vector normal, GL_Vector color, FloatBuffer floatBuffer,ShaderConfig config){
		//ti= TextureManager.getTextureInfo("mantle");


		p1 =GL_Matrix.multiply(matrix,p1);

		p2 =GL_Matrix.multiply(matrix,p2);
		p3 =GL_Matrix.multiply(matrix,p3);
		p4 =GL_Matrix.multiply(matrix,p4);
		normal=GL_Matrix.multiply(matrix,normal);
		floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
		floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
		floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
		floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
		floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
		floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
	}

	public static GL_Vector getVectorFromXZAngle(float angle){
		GL_Vector viewDir =new GL_Vector();
		viewDir.x = (float) Math.cos(angle);
		viewDir.z = (float) Math.sin(angle);
		return viewDir;
	}
    public static float getAnagleFromXZVectory(GL_Vector dir){


       float angle = (float)Math.atan(dir.z/dir.x);
        if(dir.z<0 && dir.x<0){
            angle+=Math.PI;
        }else if(dir.z>0 && dir.x<0){
            angle+=Math.PI;
        }
        return angle;
    }


}