package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.math.AABB;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/3/5.
 */
public class BaseProperties extends cola.machine.game.myblocks.model.AABB.AABB{
    int id ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /* protected float x;
        protected float y;
        protected float z;*/
    protected GL_Vector position =new GL_Vector() ;
    protected GL_Vector walkDir =new GL_Vector() ;
    protected  GL_Vector viewDir =new GL_Vector() ;


    protected String name;
    protected String pwd;
    protected float bodyAngle;
    protected float headAngle;
    protected float headAngle2;


    public GL_Vector getPosition() {
        return position;
    }

   /* public void setPosition(GL_Vector position) {
        this.position = position;
    }
*/
    public GL_Vector getWalkDir() {
        return walkDir;
    }

    public void setWalkDir(GL_Vector walkDir) {
        this.walkDir = walkDir;
    }

    public GL_Vector getViewDir() {
        return viewDir;
    }

    public void setViewDir(GL_Vector viewDir) {
        this.viewDir = viewDir;
    }

    public float getBodyAngle() {
        return bodyAngle;
    }

    public void setBodyAngle(float bodyAngle) {
        this.bodyAngle = bodyAngle;
    }

    public float getHeadAngle() {
        return headAngle;
    }

    public void setHeadAngle(float headAngle) {
        this.headAngle = headAngle;
    }

    public float getHeadAngle2() {
        return headAngle2;
    }

    public void setHeadAngle2(float headAngle2) {
        this.headAngle2 = headAngle2;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public float getX() {
        return position.x;
    }

    public void setX(float x) {
        this.position.x = x;
    }

    public float getY() {
        return position.y;
    }

    public void setY(float y) {
        this.position.y = y;
    }

    public float getZ() {
        return position.z;
    }

    public void setZ(float z) {
        this.position.z = z;
    }

    public void setPosition(GL_Vector position) {

        setPosition(position.x,position.y,position.z);
        //
    }

    public void setPosition(float posx, float posy, float posz) {
        this.minX=posx-0.5f;
        this.minY=posy;
        this.minZ=posz-0.5f;

        this.maxX=posx+0.5f;
        this.maxY=posy+4;
        this.maxZ=posz+0.5f;
        //
       /* position.x= posx;
        position.y = posy;
        position.z = posz;*/
        this.setX(posx);
        this.setY(posy);
        this.setZ(posz);
       /* this.position.x=posx;
        this.position.y=posy;
        this.position.z=posz;*/



    }

}
