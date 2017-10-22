package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.BagCmd;
import com.dozenx.game.engine.command.DropCmd;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/18.
 */
public class DropHandler extends GameServerHandler {
    private EnemyService enemyService;
    private UserService userService;
    private BagService bagService;
    public DropHandler(ServerContext serverContext){
        super(serverContext);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
        userService = (UserService)serverContext.getService(UserService.class);
        bagService =(BagService)serverContext.getService(BagService.class);
    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        DropCmd cmd =(DropCmd) request.getCmd();

        if(cmd.getUserId()!=0) {
            LivingThingBean from = userService.getOnlinePlayerById(cmd.getUserId());
            if (from != null) {
                ItemServerBean[] itemAry = bagService.getItemAryUserId(from.getId());

                for (int i = 0; i < itemAry.length; i++) {
                    if (itemAry[i] != null && itemAry[i].getId() == cmd.getItemId()) {
//                        itemAry[i].setX(from.getX());
//                        itemAry[i].setY(from.getY());
//                        itemAry[i].setZ(from.getZ());

                        bagService.addWorldItem(itemAry[i]);
                        cmd.setItemType(itemAry[i].getItemType());
                        cmd.setNum(itemAry[i].getNum());
                        //计算丢物品的角度
                        float bodyAngle = from.getBodyAngle();
                        GL_Vector bodyDir = GL_Vector.getVectorFromXZAngle(bodyAngle);
                        GL_Vector postion
                                = GL_Vector.add(new GL_Vector(from.getX(), from.getY(), from.getZ()), GL_Vector.multiply(bodyDir, 5));
                        itemAry[i].setX(postion.x);
                        itemAry[i].setY(postion.y);
                        itemAry[i].setZ(postion.z);
                        itemAry[i].dropTime=cmd.getDropTime();
                        cmd.setX(postion.x);
                        cmd.setY(postion.y);
                        cmd.setZ(postion.z);
                        itemAry[i] = null;
                        broadCast(cmd);//发给别人我丢了什么东西 分发drop时间
                        BagCmd bagCmd = new BagCmd(from.getId(), bagService.getItemByUserId(from.getId()));
                        request.getWorker().send(bagCmd.toBytes());//重新同步背包数据
                        return null;
                    }
                }
                //如果都没有遍历到那么强制更新物品包信息
                BagCmd bagCmd = new BagCmd(from.getId(), from.getItemBeansList());
                request.getWorker().send(bagCmd.toBytes());
                //worldItem add something

            }
        }else{
            ItemServerBean itemServerBean =new ItemServerBean();
            itemServerBean.setItemType(cmd.getItemType());
            itemServerBean.setId(cmd.getItemId());
            itemServerBean.setX(cmd.getX());
            itemServerBean.setY(cmd.getY());
            itemServerBean.setZ(cmd.getZ());
            itemServerBean.dropTime = cmd.getDropTime();
            bagService.addWorldItem(itemServerBean);
            broadCast(cmd);//发给别人我丢了什么东西 分发drop时间
        }


        //更新其他附近人的此人的装备属性
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
