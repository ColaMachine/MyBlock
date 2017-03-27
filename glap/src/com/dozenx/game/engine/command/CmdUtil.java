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
        }else  if (cmdTypeVal ==  CmdType.ATTACK.ordinal()){
            return new AttackCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.BAG.ordinal()){
            return new BagCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.BAGCHANGE.ordinal()){
            return new BagChangeCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.GET.ordinal()){
            return new GetCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.PICK.ordinal()){
            return new PickCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.DROP.ordinal()){
            return new DropCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.WALK.ordinal()){
            return new WalkCmd2(bytes);
        }else  if (cmdTypeVal ==  CmdType.DIED.ordinal()){
            return new DiedCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.REBORN.ordinal()){
            return new RebornCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.CHASE.ordinal()){
            return new ChaseCmd(bytes);
        }else  if (cmdTypeVal ==  CmdType.JUMP.ordinal()){
            return new JumpCmd(bytes);
        }else{
            LogUtil.err("can't recgnize the cmd");
            return null;
        }

    }
}
