package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.math.AABB;
import com.dozenx.game.network.server.bean.PlayerStatus;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/3/5.
 */
public class BaseProperties extends cola.machine.game.myblocks.model.AABB.AABB{

    public void getInfo(PlayerStatus info) {
        info.setId(getId());
        info.setName(getName());

        //setPwd(status.getPwd());
        info. setBodyAngle(getBodyAngle());
        info. setHeadAngle(getHeadAngle());
        info. setHeadAngle2(getHeadAngle2());

       // info. setTargetId(getTargetId());

        // role. setIsplayer(status.isplayer);

        //this.id = status.getId();
        info.setX(getX());
        info.setY(getY());
        info.setZ(getZ());

    }

    public void setInfo(PlayerStatus info ){

        this.setId(info.getId());
        this.setName(info.getName());
        this.setPwd(info.getPwd());
        //setPwd(status.getPwd());
        this. setBodyAngle(info.getBodyAngle());
        this. setHeadAngle(info.getHeadAngle());
        this. setHeadAngle2(info.getHeadAngle2());
        this.setPosition(info.getX(),info.getY(),info.getZ());
  }
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
    public GL_Vector position =new GL_Vector() ;
    public GL_Vector walkDir =new GL_Vector() ;
    public  GL_Vector viewDir =new GL_Vector() ;


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