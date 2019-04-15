package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.filetransfer.FileRecvResult;
import com.dozenx.game.network.server.filetransfer.FileSec;
import core.log.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by luying on 17/2/18.
 */
public class FileHandler extends GameServerHandler {

    public FileHandler(ServerContext serverContext) {
        super(serverContext);

    }

    //  public static Map<Integer ,FileOutputStream> outStreamMap =new HashMap<Integer ,FileOutputStream>();
    public static Map<Integer, FileRecvResult> fileRecvResultMap = new HashMap<Integer, FileRecvResult>();

    public ResultCmd handler(GameServerRequest request, GameServerResponse response) throws Exception {
        //===========根据block的位置获取boxid according to block position to get boxId
        GameCmd cmd = request.getCmd();
        if (cmd.getCmdType() == CmdType.FileStart) {

            FileStartCmd fileStartCmd = (FileStartCmd) cmd;
            //拿到要保存的位置 根部 文件夹目录 这里假设是 ~/Document
          //  Path path = Paths.get("/Users/luying/Documents/test");
           // Path path = Paths.get("G:/test/to");

            Path path = Paths.get("/media/colamachine/924A7E084A7DE97D/mac");

            LogUtil.println("开始接收文件 : " + fileStartCmd.getFileName() + " taskId:" + fileStartCmd.getTaskId());

            File file = path.resolve(fileStartCmd.getFileName()).toFile();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                LogUtil.println("创建文件夹" + file.getParentFile());
            }


            //当前的文件是这个
            try {

                LogUtil.println("创建写入流 create the write stream ");

                FileOutputStream out = new FileOutputStream(file);
                FileRecvResult fileRecvResult = new FileRecvResult();
                fileRecvResult.fileOutputStream = out;
                fileRecvResult.fileName = fileStartCmd.getFileName();
                fileRecvResult.fileSize = fileStartCmd.getFileSize();
                fileRecvResult.filePath = path.resolve(fileStartCmd.getFileName());
                fileRecvResult.startTime = System.currentTimeMillis();
                fileRecvResultMap.put(fileStartCmd.getTaskId(), fileRecvResult);
                // outStreamMap.put(fileStartCmd.getTaskId(),out);
                //register this out  remember this out

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            //开始创建文件 start create file
        } else if (cmd.getCmdType() == CmdType.FileData) {
            System.out.print(".");
            FileDataCmd fileDataCmd = (FileDataCmd) cmd;
            try {
                // FileOutputStream out =outStreamMap.get(fileDataCmd.getTaskId());
                FileRecvResult recvResult = fileRecvResultMap.get(fileDataCmd.getTaskId());
                FileSec fileSec =new FileSec();
                fileSec.startPos= fileDataCmd.startPos;
                if(fileSec.startPos!=  recvResult.nowWriteOffset+1){
                    LogUtil.err("文件的写入位置不对");
                  throw new Exception("文件的写入位置不对");

                }

                fileSec.length = fileDataCmd.getData().length;


                recvResult.fileSecList.add(fileSec);
                recvResult.fileOutputStream.write(fileDataCmd.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (cmd.getCmdType() == CmdType.FileEnd) {
            FileEndCmd fileEndCmd = (FileEndCmd) cmd;
            try {
                FileRecvResult recvResult = fileRecvResultMap.get(fileEndCmd.getTaskId());
                recvResult.fileOutputStream.close();
                //检查文件的大小和原来定义的是否一致
                LogUtil.println("close this file out put stream");
                return new ResultCmd(0, "success"+recvResult.fileName, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
        //更新其他附近人的此人的装备属性
    }

}
