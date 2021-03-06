/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cola.machine.game.myblocks.math;

//import com.bulletphysics.linearmath.AabbUtil2;
//import com.bulletphysics.linearmath.Transform;

import cola.machine.game.myblocks.engine.Constants;
import com.google.common.base.Objects;
import glmodel.GL_Vector;
import gnu.trove.list.TFloatList;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An axis-aligned bounding box. Provides basic support for inclusion
 * and intersection tests.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public final class AABB {

    private final Vector3f min;
    private final Vector3f max;

    private Vector3f[] vertices;

    public AABB(Vector3f min, Vector3f max) {
        this.min = min;
        this.max = max;
    }

    public static AABB createMinMax(Vector3f min, Vector3f max) {
        return new AABB(new Vector3f(min), new Vector3f(max));
    }

    public static AABB createCenterExtent(Vector3f center, Vector3f extent) {
        Vector3f min = new Vector3f(center);
        min.sub(extent);
        Vector3f max = new Vector3f(center);
        max.add(extent);
        return new AABB(min, max);
    }

    public static AABB createEmpty() {
        return new AABB(new Vector3f(), new Vector3f());
    }

    /**
     * Creates a new AABB that encapsulates a set of AABBs
     *
     * @param others
     */
    public static AABB createEncompassing(Iterable<AABB> others) {
        Vector3f min;
        Vector3f max;
        Iterator<AABB> i = others.iterator();
        if (i.hasNext()) {
            AABB next = i.next();
            min = next.getMin();
            max = next.getMax();
        } else {
            return createEmpty();
        }
        while (i.hasNext()) {
            AABB next = i.next();
            Vector3f otherMin = next.getMin();
            Vector3f otherMax = next.getMax();
            Vector3fUtil.min(min, otherMin, min);
            Vector3fUtil.max(max, otherMax, max);
        }
        return new AABB(min, max);
    }

    public static AABB createEncompasing(TFloatList vertices) {
        int vertexCount = vertices.size() / 3;
        if (vertexCount == 0) {
            return AABB.createEmpty();
        }

        Vector3f min = new Vector3f(vertices.get(0), vertices.get(1), vertices.get(2));
        Vector3f max = new Vector3f(vertices.get(0), vertices.get(1), vertices.get(2));
        for (int index = 1; index < vertexCount; ++index) {
            min.x = Math.min(min.x, vertices.get(3 * index));
            max.x = Math.max(max.x, vertices.get(3 * index));
            min.y = Math.min(min.y, vertices.get(3 * index + 1));
            max.y = Math.max(max.y, vertices.get(3 * index + 1));
            min.z = Math.min(min.z, vertices.get(3 * index + 2));
            max.z = Math.max(max.z, vertices.get(3 * index + 2));
        }
        return AABB.createMinMax(min, max);
    }

    public Vector3f getExtents() {
        Vector3f dimensions = new Vector3f(max);
        dimensions.sub(min);
        dimensions.scale(0.5f);
        return dimensions;
    }

    public Vector3f getCenter() {
        Vector3f dimensions = new Vector3f(max);
        dimensions.add(min);
        dimensions.scale(0.5f);
        return dimensions;
    }

    public Vector3f getMin() {
        return new Vector3f(min);
    }

    public Vector3f getMax() {
        return new Vector3f(max);
    }

    public AABB move(Vector3f offset) {
        Vector3f newMin = new Vector3f(min);
        newMin.add(offset);
        Vector3f newMax = new Vector3f(max);
        newMax.add(offset);
        return new AABB(newMin, newMax);
    }

   /* public AABB transform(Quat4f rotation, Vector3f offset, float scale) {
        Transform transform = new Transform(new Matrix4f(rotation, offset, scale));
        Vector3f newMin = new Vector3f();
        Vector3f newMax = new Vector3f();
        AabbUtil2.transformAabb(min, max, 0.01f, transform, newMin, newMax);
        return new AABB(newMin, newMax);
    }

    public AABB transform(Transform transform) {
        Vector3f newMin = new Vector3f();
        Vector3f newMax = new Vector3f();
        AabbUtil2.transformAabb(min, max, 0.01f, transform, newMin, newMax);
        return new AABB(newMin, newMax);
    }
*/
    /**
     * Returns true if this AABB overlaps the given AABB.
     *
     * @param aabb2 The AABB to check for overlapping
     * @return True if overlapping
     */
    public boolean overlaps(AABB aabb2) {
        return !(max.x < aabb2.min.x || min.x > aabb2.max.x)
                && !(max.y < aabb2.min.y || min.y > aabb2.max.y)
                && !(max.z < aabb2.min.z || min.z > aabb2.max.z);
    }

    /**
     * Returns true if the AABB contains the given point.
     *
     * @param point The point to check for inclusion
     * @return True if containing
     */
    public boolean contains(Vector3d point) {
        return !(max.x < point.x || min.x >= point.x)
                && !(max.y < point.y || min.y >= point.y)
                && !(max.z < point.z || min.z >= point.z);
    }

    /**
     * Returns true if the AABB contains the given point.
     *
     * @param point The point to check for inclusion
     * @return True if containing
     */
    public boolean contains(Vector3f point) {
        return !(max.x < point.x || min.x >= point.x)
                && !(max.y < point.y || min.y >= point.y)
                && !(max.z < point.z || min.z >= point.z);
    }

    /**
     * Returns the closest point on the AABB to a given point.
     *
     * @param p The point
     * @return The point on the AABB closest to the given point
     */
    public Vector3f closestPointOnAABBToPoint(Vector3f p) {
        Vector3f r = new Vector3f(p);

        if (p.x < min.x) {
            r.x = min.x;
        }
        if (p.x > max.x) {
            r.x = max.x;
        }
        if (p.y < min.y) {
            r.y = min.y;
        }
        if (p.y > max.y) {
            r.y = max.y;
        }
        if (p.z < min.z) {
            r.z = min.z;
        }
        if (p.z > max.z) {
            r.z = max.z;
        }

        return r;
    }

    public Vector3f getFirstHitPlane(Vector3f direction, Vector3f pos, Vector3f dimensions, boolean testX, boolean testY, boolean testZ) {
        Vector3f hitNormal = new Vector3f();

        float dist = Float.POSITIVE_INFINITY;

        if (testX) {
            float distX;
            if (direction.x > 0) {
                distX = (min.x - pos.x - dimensions.x) / direction.x;
            } else {
                distX = (max.x - pos.x + dimensions.x) / direction.x;
            }
            if (distX >= 0 && distX < dist) {
                hitNormal.set(Math.copySign(1, direction.x), 0, 0);
            }
        }
        if (testY) {
            float distY;
            if (direction.y > 0) {
                distY = (min.y - pos.y - dimensions.y) / direction.y;
            } else {
                distY = (max.y - pos.y + dimensions.y) / direction.y;
            }
            if (distY >= 0 && distY < dist) {
                hitNormal.set(0, Math.copySign(1, direction.y), 0);
            }
        }
        if (testZ) {
            float distZ;
            if (direction.z > 0) {
                distZ = (min.z - pos.z - dimensions.z) / direction.z;
            } else {
                distZ = (max.z - pos.z + dimensions.z) / direction.z;
            }
            if (distZ >= 0 && distZ < dist) {
                hitNormal.set(0, 0, Math.copySign(1, direction.z));
            }
        }
        return hitNormal;

    }

    /**
     * Returns the normal of the plane closest to the given origin.
     *
     * @param pointOnAABB A point on the AABB
     * @param origin      The origin
     * @param testX       True if the x-axis should be tested
     * @param testY       True if the y-axis should be tested
     * @param testZ       True if the z-axis should be tested
     * @return The normal
     */
    public Vector3f normalForPlaneClosestToOrigin(Vector3f pointOnAABB, Vector3f origin, boolean testX, boolean testY, boolean testZ) {
        List<Vector3f> normals = new ArrayList<>();

        if (pointOnAABB.z == min.z && testZ) {
            normals.add(new Vector3f(0, 0, -1));
        }
        if (pointOnAABB.z == max.z && testZ) {
            normals.add(new Vector3f(0, 0, 1));
        }
        if (pointOnAABB.x == min.x && testX) {
            normals.add(new Vector3f(-1, 0, 0));
        }
        if (pointOnAABB.x == max.x && testX) {
            normals.add(new Vector3f(1, 0, 0));
        }
        if (pointOnAABB.y == min.y && testY) {
            normals.add(new Vector3f(0, -1, 0));
        }
        if (pointOnAABB.y == max.y && testY) {
            normals.add(new Vector3f(0, 1, 0));
        }

        float minDistance = Float.MAX_VALUE;
        Vector3f closestNormal = new Vector3f();

        for (int i = 0; i < normals.size(); i++) {
            Vector3f n = normals.get(i);

            Vector3f diff = new Vector3f(centerPointForNormal(n));
            diff.sub(origin);

            float distance = diff.length();

            if (distance < minDistance) {
                minDistance = distance;
                closestNormal = n;
            }
        }

        return closestNormal;
    }

    /**
     * Returns the center point of one of the six planes for the given normal.
     *
     * @param normal The normal
     * @return The center point
     */
    public Vector3f centerPointForNormal(Vector3f normal) {
        if (normal.x == 1 && normal.y == 0 && normal.z == 0) {
            return new Vector3f(max.x, getCenter().y, getCenter().z);
        }
        if (normal.x == -1 && normal.y == 0 && normal.z == 0) {
            return new Vector3f(min.x, getCenter().y, getCenter().z);
        }
        if (normal.x == 0 && normal.y == 0 && normal.z == 1) {
            return new Vector3f(getCenter().x, getCenter().y, max.z);
        }
        if (normal.x == 0 && normal.y == 0 && normal.z == -1) {
            return new Vector3f(getCenter().x, getCenter().y, min.z);
        }
        if (normal.x == 0 && normal.y == 1 && normal.z == 0) {
            return new Vector3f(getCenter().x, max.y, getCenter().z);
        }
        if (normal.x == 0 && normal.y == -1 && normal.z == 0) {
            return new Vector3f(getCenter().x, min.y, getCenter().z);
        }

        return new Vector3f();
    }

    public float minX() {
        return min.x;
    }

    public float minY() {
        return min.y;
    }

    public float minZ() {
        return min.z;
    }

    public float maxX() {
        return max.x;
    }

    public float maxY() {
        return max.y;
    }

    public float maxZ() {
        return max.z;
    }

    /**
     * Returns the vertices of this AABB.
     *
     * @return The vertices
     */
    public Vector3f[] getVertices() {
        if (vertices == null) {
            vertices = new Vector3f[8];

            // Front
            vertices[0] = new Vector3f(min.x, min.y, max.z);
            vertices[1] = new Vector3f(max.x, min.y, max.z);
            vertices[2] = new Vector3f(max.x, max.y, max.z);
            vertices[3] = new Vector3f(min.x, max.y, max.z);
            // Back
            vertices[4] = new Vector3f(min.x, min.y, min.z);
            vertices[5] = new Vector3f(max.x, min.y, min.z);
            vertices[6] = new Vector3f(max.x, max.y, min.z);
            vertices[7] = new Vector3f(min.x, max.y, min.z);
        }

        return vertices;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AABB) {
            AABB other = (AABB) obj;
            return Objects.equal(min, other.min) && Objects.equal(max, other.max);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(min, max);
    }
    //from = {javax.vecmath.Vector3f@3111}"(1.973442, 3.0, -8.617493)"
    //direction = {javax.vecmath.Vector3f@3112}"(-0.107100524, -0.36650136, 0.9242328)"
    public boolean intersectRectangle(Vector3f from, Vector3f direction) {

        //(0.5, 65.0, -40.5)  //(1.5, 66.5, -39.5)  //(-0.044776313, 0.5225283, -0.8514454)

        GL_Vector newMin = new GL_Vector();
        //min = {javax.vecmath.Vector3f@2756}"(0.5, 1.0, 0.5)"
        newMin.x= min.x - from.x;
        newMin.y= min.y - from.y;
        newMin.z= min.z - from.z;
       // LogUtil.println("newMin:"+newMin);
        //max = {javax.vecmath.Vector3f@3106}"(1.5, 2.5, 1.5)"
        GL_Vector newMax = new GL_Vector();
        newMax.x= max.x - from.x;
        newMax.y= max.y - from.y;
        newMax.z= max.z - from.z;
      //  LogUtil.println("newMax:"+newMax);


        //四线段交集问题

        //minX minY maxX maxY
//       boolean xy = ;
//        boolean xz = ;
//        boolean yz = ;
        if(jiaoji(newMin.x,newMin.y,newMax.x,newMax.y,direction.x/direction.y)
                &&
                jiaoji(newMin.x,newMin.z,newMax.x,newMax.z,direction.x/direction.z)
                && jiaoji(newMin.y,newMin.z,newMax.y,newMax.z,direction.y/direction.z) ){
            return true;
        }

        return false;
    }


    public float[] intersectRectangle2(Vector3f from, Vector3f direction) {

        //(0.5, 65.0, -40.5)  //(1.5, 66.5, -39.5)  //(-0.044776313, 0.5225283, -0.8514454)

        GL_Vector newMin = new GL_Vector();
        //min = {javax.vecmath.Vector3f@2756}"(0.5, 1.0, 0.5)"
        newMin.x= min.x - from.x;
        newMin.y= min.y - from.y;
        newMin.z= min.z - from.z;
        // LogUtil.println("newMin:"+newMin);
        //max = {javax.vecmath.Vector3f@3106}"(1.5, 2.5, 1.5)"
        GL_Vector newMax = new GL_Vector();
        newMax.x= max.x - from.x;
        newMax.y= max.y - from.y;
        newMax.z= max.z - from.z;
        //  LogUtil.println("newMax:"+newMax);


        //四线段交集问题

        //minX minY maxX maxY
//       boolean xy = ;
//        boolean xz = ;
//        boolean yz = ;


        float[] xyResult = jiaoji2(newMin.x,newMin.y,newMax.x,newMax.y,direction.x,direction.y);
        if(xyResult==null){
            return null;
        }

        float[] xzResult = jiaoji2(newMin.x,newMin.z,newMax.x,newMax.z,direction.x,direction.z) ;
        if(xzResult==null){
            return null;
        }
        float[] yzResult = jiaoji2(newMin.y,newMin.z,newMax.y,newMax.z,direction.y,direction.z) ;
        if(yzResult==null){
            return null;
        }

        return  new float[]{xyResult[1],yzResult[1],xzResult[2],xyResult[0],xzResult[0],yzResult[0]};

    }
    public static float[][] xyFaces ={
            {Constants.LEFT,Constants.FRONT,Constants.BACK},//1
            {Constants.RIGHT,Constants.FRONT,Constants.BACK},//2
            {Constants.BOTTOM,Constants.FRONT,Constants.BACK},//3
            {Constants.TOP,Constants.FRONT,Constants.BACK},//3
    };

    public static float[][] xzFaces ={
            {Constants.LEFT,Constants.BOTTOM,Constants.TOP},//1
            {Constants.RIGHT,Constants.BOTTOM,Constants.TOP},//2
            {Constants.BACK,Constants.BOTTOM,Constants.TOP},//3
            {Constants.FRONT,Constants.BOTTOM,Constants.TOP},//3
    };

    public static float[][] yzFaces ={
            {Constants.BOTTOM,Constants.LEFT,Constants.RIGHT},//1
            {Constants.TOP,Constants.LEFT,Constants.RIGHT},//2
            {Constants.BACK,Constants.LEFT,Constants.RIGHT},//3
            {Constants.FRONT,Constants.LEFT,Constants.RIGHT},//3 //z的最大值方向过来 且和z边框相交
    };
    public boolean chuizhijuli(Vector3f from, Vector3f direction) {
        Vector3f dirfrac = new Vector3f();
        //(0.5, 65.0, -40.5)  //(1.5, 66.5, -39.5)  //(-0.044776313, 0.5225283, -0.8514454)

        GL_Vector newMin = new GL_Vector();
        newMin.x= min.x - from.x;
        newMin.y= min.y - from.y;
        newMin.z= min.z - from.z;

        GL_Vector newMax = new GL_Vector();
        newMax.x= max.x - from.x;
        newMax.y= max.y - from.y;
        newMax.z= max.z - from.z;


        dirfrac.x = 1.0f / direction.x;
        dirfrac.y = 1.0f / direction.y;
        dirfrac.z = 1.0f / direction.z;

        float t1 = (min.x - from.x) * dirfrac.x;
        float t2 = (max.x - from.x) * dirfrac.x;
        float t3 = (min.y - from.y) * dirfrac.y;
        float t4 = (max.y - from.y) * dirfrac.y;
        float t5 = (min.z - from.z) * dirfrac.z;
        float t6 = (max.z - from.z) * dirfrac.z;

        float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

        if (tmax < 0) {
            return false;
        }

        if (tmin > tmax) {
            return false;
        }

        return true;
    }


    public boolean xiangjiao(Vector3f form,Vector3f vector){
    //先得到方程

        float x0 = min.x;
        float x1=max.x;
        return false;
    }
    public static void main(String args[]){
        AABB aabb = new AABB(new Vector3f(-0.5f,4,-0.5f) ,new Vector3f(0.5f,5.5f,0.5f));
      Vector3f from = new Vector3f(-0.37008464f, 3.0f, 6.9974623f);
        Vector3f vector3f=new Vector3f(-0.110462874f, 3.7768552f, -21.9966764f);

        System.out.print(aabb.intersectRectangle(from,vector3f));
    }

    public static boolean jiaoji(float minX,float minY,float maxX,float maxY, float ratioXY){

        //minY
        float value = ratioXY*minY;
        if(value>=minX && value<=maxX){
            return true;
        }

        //maxY
        value = ratioXY*maxY;
        if(value>=minX && value<=maxX){
            return true;
        }


        //minX

        value = minX / ratioXY;
        if(value>=minY && value<=maxY){
            return true;
        }
        value = maxX / ratioXY;
        if(value>=minY && value<=maxY){
            return true;
        }
        return false;
    }

    /**
     *
     * @param minX
     * @param minY
     * @param maxX
     * @param maxY
     * @param ratioXY
     * @return
     *  1代表第1个参数最小那边 2代表第1个参数最大那边 3代表第2个参数最小那边 4代表第二个参数最大那边 最后那个参数是对应起始点的边上的点的位置
     *  一版一个线段和立方体交际 或者 方形相交 一定会 碰上两条线段 我们要拿到是最短的那条
     *   数组二代表第一个维度的位置  数组
     */
    public static float[] jiaoji2(float minX,float minY,float maxX,float maxY, float dirX,float dirY){

        //minY
        float ratioXY = dirX/dirY;
        float distance=0,_tempDistance =0;
        float value = ratioXY*minY;
        float[] result = null ;
        if(minY*maxY<0)//说明原点在他们中间
        {
            if(dirY<0){//说明朝下的 就没maxY什么事情了
                if (value >= minX && value <= maxX) {//是否跟下线段交集
                    _tempDistance = value * value + minY * minY;
                    if (distance == 0 || _tempDistance < distance) {
                        distance = _tempDistance;
                        result = new float[]{3, value, minY, distance};
                    }
                }
            }else{//说明朝上的 就没minY什么事情了

            }
        }else {//说明都在同一边
            if (value >= minX && value <= maxX) {//是否跟下线段交集
                _tempDistance = value * value + minY * minY;
                if (distance == 0 || _tempDistance < distance) {
                    distance = _tempDistance;
                    result = new float[]{3, value, minY, distance};
                }
            }
        }

        //maxY
        if(minY*maxY<0)//说明原点在他们中间
        {
            if(dirY<0){//说明朝下的 就没maxY什么事情了

            }else{//说明朝上的 就没minY什么事情了
                value = ratioXY*maxY;
                if(value>=minX && value<=maxX){//上面
                    _tempDistance = value*value + maxY*maxY;
                    if(distance==0 || _tempDistance<distance){
                        distance=_tempDistance;
                        result = new float[]{4,value,maxY,distance};
                    }

                }
            }
        }else {//说明都在同一边
            value = ratioXY*maxY;
            if(value>=minX && value<=maxX){//上面
                _tempDistance = value*value + maxY*maxY;
                if(distance==0 || _tempDistance<distance){
                    distance=_tempDistance;
                    result = new float[]{4,value,maxY,distance};
                }

            }
        }




        //minX


        if(minX*maxX<0)//说明原点在他们中间
        {
            if(dirX<0){//说明朝下的 就没maxX什么事情了
                value = minX / ratioXY;
                if(value>=minY && value<=maxY){//低x 左侧
                    _tempDistance = value*value + minX*minX;
                    if(distance==0 || _tempDistance<distance){
                        distance=_tempDistance;
                        result = new float[]{1,minX,value,distance};
                    }
                }
            }else{//说明朝上的 就没minY什么事情了

            }
        }else {//说明都在同一边
            value = minX / ratioXY;
            if(value>=minY && value<=maxY){//低x 左侧
                _tempDistance = value*value + minX*minX;
                if(distance==0 || _tempDistance<distance){
                    distance=_tempDistance;
                    result = new float[]{1,minX,value,distance};
                }
            }
        }


        //maxX


        if(minX*maxX<0)//说明原点在他们中间
        {
            if(dirX<0){//说明朝下的 就没maxX什么事情了

            }else{//说明朝上的 就没minY什么事情了
                value = maxX / ratioXY;
                if(value>=minY && value<=maxY){//下面
                    _tempDistance = value*value + maxX*maxX;
                    if(distance==0 || _tempDistance<distance){
                        distance=_tempDistance;
                        result = new float[]{2,maxX,value,distance};
                    }
                }
            }
        }else {//说明都在同一边
            value = maxX / ratioXY;
            if(value>=minY && value<=maxY){//下面
                _tempDistance = value*value + maxX*maxX;
                if(distance==0 || _tempDistance<distance){
                    distance=_tempDistance;
                    result = new float[]{2,maxX,value,distance};
                }
            }
        }

        return result;
    }
}
