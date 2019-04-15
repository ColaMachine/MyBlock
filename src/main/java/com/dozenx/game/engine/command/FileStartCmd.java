package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 *
 * Im ready to send file fileName is xxxxx  relative folder is xxxxx fileSize is
 * taskid is xxxx
 *
 *
 * ok I will received / no i eject
 *
 *
 * this is only one file /
 * Created by luying on 17/2/7.
 */
public class FileStartCmd extends   BaseGameCmd{//start[fileName fileSize taskId] data[taskId frame1] data[taskId frame2] ....  end[taskId]
    private String fileName;

    public FileStartCmd(){

    }
    private long fileSize;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    private int taskId;



    byte[] data;


    final CmdType cmdType = CmdType.FileStart;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FileStartCmd(byte[] bytes){

        parse(bytes);
    }


    //|length|threadId|userName|lengthPwd|
    public byte[] toBytes(){

        return ByteUtil.createBuffer().put(cmdType.getType())
                .putLenStr(this.fileName)
                .put(fileSize)
                .put(taskId)
               .array();


        // ByteUtil.createBuffer().put(userNameLength);

    }




    public void parse(byte[] bytes){

        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        fileName = byteBufferWrap.getLenStr();
        fileSize = byteBufferWrap.getLong();
        taskId =byteBufferWrap.getInt();




    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
