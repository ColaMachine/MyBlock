package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import core.log.LogUtil;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luying on 17/2/18.
 */
public class FileHandler extends GameServerHandler {

    public FileHandler(ServerContext serverContext){
        super(serverContext);

    }
    public static Map<Integer ,FileOutputStream> outStreamMap =new HashMap<Integer ,FileOutputStream>();
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){
        //===========根据block的位置获取boxid according to block position to get boxId
        GameCmd cmd = request.getCmd();
        if(cmd.getCmdType() == CmdType.FileStart){

            FileStartCmd fileStartCmd = (FileStartCmd ) cmd;
            //拿到要保存的位置 根部 文件夹目录 这里假设是 ~/Document
            Path path = Paths.get("/Users/luying/Documents/test");
            LogUtil.println("开始接收文件 : "+fileStartCmd.getFileName()+" taskId:"+fileStartCmd.getTaskId() );

            File file = path.resolve(fileStartCmd.getFileName()).toFile();
           if( !file.getParentFile().exists()){
               file.getParentFile().mkdirs();
               LogUtil.println("创建文件夹"+file.getParentFile());
           }


            //当前的文件是这个
            try {

                LogUtil.println("创建写入流 create the write stream ");

                FileOutputStream out  = new FileOutputStream(file);
                outStreamMap.put(fileStartCmd.getTaskId(),out);
                //register this out  remember this out

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            //开始创建文件 start create file
        }else if(cmd.getCmdType() == CmdType.FileData){
            FileDataCmd fileDataCmd = (FileDataCmd ) cmd;

            try {

                FileOutputStream out =outStreamMap.get(fileDataCmd.getTaskId());
                out.write(fileDataCmd.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(cmd.getCmdType() == CmdType.FileEnd){
            FileEndCmd fileEndCmd = (FileEndCmd ) cmd;

            try {
                FileOutputStream out =outStreamMap.get(fileEndCmd.getTaskId());
                out.close();

                LogUtil.println("close this file out put stream");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return new ResultCmd(0,"success",0);

        //更新其他附近人的此人的装备属性

    }

}
