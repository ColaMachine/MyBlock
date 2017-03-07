package com.dozenx.game.engine.command;

import com.dozenx.game.network.server.bean.PlayerStatus;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class PlayerSynCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.PLAYERSTATUS;
    private PlayerStatus playerStatus;

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public PlayerSynCmd(byte[] bytes){
        parse(bytes);
    }
    public PlayerSynCmd(PlayerStatus playerStatus){

        this.playerStatus = playerStatus;


    }

    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){
       /* private float x;
        private float y;
        private float z;


        private int bodyAngle;
        private int headAngle;
        private int headAngle2;
        private byte headEquip;
        private byte bodyEquip;//省体装备
        private byte handEquip;//手部装备
        private byte shoeEquip;
        private byte legEquip;//腿部装备
        private int id;//id
        private long loginTime;//下线时间
        private long logoffTime;//登录时间
        private int targetId;//目标
        private boolean isplayer;//是否是玩家
        private byte type;//生物种类

        private String name;
        private String pwd;
*/
        return  ByteUtil.createBuffer()
            .put(CmdType.PLAYERSTATUS.getType())
            .put( playerStatus.getX())
            .put(playerStatus.getY())
            .put(playerStatus.getZ())
            .put(playerStatus.getBodyAngle())
            .put(playerStatus.getHeadAngle())
            .put(playerStatus.getHeadAngle2())
            .put(playerStatus.getHeadEquip())
            .put(playerStatus.getBodyEquip())
            .put(playerStatus.getHandEquip())
            .put(playerStatus.getLegEquip())
            .put(playerStatus.getFootEquip())
            .put(playerStatus.getId())
            .put(playerStatus.isPlayer()).array();


    }
    public void parse(byte[] bytes){
        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);

        this.playerStatus =new PlayerStatus();

        byteBufferWrap.getInt();

        playerStatus.setX(byteBufferWrap.getFloat());
        playerStatus.setY(byteBufferWrap.getFloat());
        playerStatus.setZ(byteBufferWrap.getFloat());
        playerStatus.setBodyAngle(byteBufferWrap.getFloat());
        playerStatus.setHeadAngle(byteBufferWrap.getFloat());
        playerStatus.setHeadAngle2(byteBufferWrap.getFloat());
        playerStatus.setHeadEquip(byteBufferWrap.getInt());
        playerStatus.setBodyEquip(byteBufferWrap.getInt());

        playerStatus.setHandEquip(byteBufferWrap.getInt());
        playerStatus.setLegEquip(byteBufferWrap.getInt());
        playerStatus.setFootEquip(byteBufferWrap.getInt());
        playerStatus.setId(byteBufferWrap.getInt());
        playerStatus.setIsplayer(byteBufferWrap.getBoolean());

         // byte[] bytes = ByteUtil.getBytes(byteArray,1,1);

    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
