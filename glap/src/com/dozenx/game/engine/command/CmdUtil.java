package com.dozenx.game.engine.command;

import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/18.
 */
public class CmdUtil {

    public static GameCmd getCmd(byte[] bytes){

        int cmdTypeVal= ByteUtil.getInt(bytes);
        if (cmdTypeVal == CmdType.EQUIP.ordinal()) {//equip
            return new EquipCmd(bytes);
        } else if (cmdTypeVal== CmdType.POS.ordinal()) {
            return new PosCmd(bytes);
        } else if (cmdTypeVal ==  CmdType.SAY.ordinal()) {
            return new SayCmd(bytes);
        } else  if (cmdTypeVal ==  CmdType.PLAYERSTATUS.ordinal()){
            return new PlayerSynCmd(bytes);
        } else  if (cmdTypeVal ==  CmdType.RESULT.ordinal()){
            return new ResultCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.LOGIN.ordinal()){
            return new LoginCmd(bytes);
        }else{
            LogUtil.err("can't recgnize the cmd");
            return null;
        }

    }
}