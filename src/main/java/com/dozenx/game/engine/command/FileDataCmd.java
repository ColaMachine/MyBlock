package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

/**
 * Im ready to send file fileName is xxxxx  relative folder is xxxxx fileSize is
 * taskid is xxxx
 * <p/>
 * <p/>
 * ok I will received / no i eject
 * <p/>
 * <p/>
 * this is only one file /
 * Created by luying on 17/2/7.
 */
public class FileDataCmd extends BaseGameCmd {//start[fileName fileSize taskId] data[taskId frame1] data[taskId frame2] ....  end[taskId]
    private int taskId;

    public long startPos;//对应文件内的开始地址

    public int length;

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

    public FileDataCmd() {

    }

    public FileDataCmd(byte[] bytes) {

        parse(bytes);
    }


    //|length|threadId|userName|lengthPwd|
    public byte[] toBytes() {

        return this.getBuffer()
                .put(taskId)
                .put(startPos)
                .put(data.length)

                .put(data)

                .array();


        // ByteUtil.createBuffer().put(userNameLength);

    }

    public void parse(byte[] bytes) {

        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.taskId = byteBufferWrap.getInt();
        this.startPos = byteBufferWrap.getLong();
        this.length = byteBufferWrap.getInt();
        this.data = byteBufferWrap.getByteAry(length);

        if (length != data.length) {
            LogUtil.err("fileDataCmd's fielSize is not right expect:" + length + " but in fact " + this.data.length);
        }


    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
