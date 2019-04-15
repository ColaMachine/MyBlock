package com.dozenx.game.network.server.filetransfer;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 16:35 2019/4/15
 * @Modified By:
 */
public class FileRecvResult {

    public int taskId;
    public List<FileSec> fileSecList =new ArrayList<FileSec>();
    public String fileName;
    public Path filePath;
    public long fileSize;
    public boolean compeleted;
    public boolean right;
    public long startTime;
    public long nowWriteOffset=-1;//写入的位置
    //==== easy way===

    public FileOutputStream fileOutputStream=null;

}
