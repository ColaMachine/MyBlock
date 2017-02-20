package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/18.
 */
public class CmdUtil {

    public static GameCmd getCmd(byte[] bytes){
        if (bytes[0] == (byte) CmdType.EQUIP.ordinal()) {//equip
            return new EquipCmd(bytes);
        } else if (bytes[0] == (byte) CmdType.POS.ordinal()) {
            return new PosCmd(bytes);
        } else if (bytes[0] == (byte) CmdType.SAY.ordinal()) {
            return new SayCmd(bytes);
        } else  {
            return new SayCmd(bytes);
        }

    }
}
