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
public class FileDataCmd extends   BaseGameCmd{//start[fileName fileSize taskId] data[taskId frame1] data[taskId frame2] ....  end[taskId]
    private int taskId;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    byte[] data;


    final CmdType cmdType = CmdType.FileData;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    public FileDataCmd(){

    }
    public FileDataCmd(byte[] bytes){

        parse(bytes);
    }


    //|length|threadId|userName|lengthPwd|
    public byte[] toBytes(){

        return this.getBuffer()
                .put(taskId)
                .put(data.length)
                .put(data)

               .array();


        // ByteUtil.createBuffer().put(userNameLength);

    }
    public void parse(byte[] bytes){

        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.taskId = byteBufferWrap.getInt();
        int length = byteBufferWrap.getInt();
        this.data= byteBufferWrap.getByteAry(length);




    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
