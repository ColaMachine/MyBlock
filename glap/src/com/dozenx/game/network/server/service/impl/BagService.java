package com.dozenx.game.network.server.service.impl;

import cola.machine.game.myblocks.engine.paths.PathManager;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.GameServerService;
import com.dozenx.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/3/9.
 */
public class BagService extends GameServerService {

    ServerContext serverContext;
    public BagService(ServerContext serverContext){
        this.serverContext =serverContext;
        this.loadItems();
    }

    public void loadItems(){
        File item = PathManager.getInstance().getHomePath().resolve("saves").resolve("item").toFile();
        if(!item.exists()){
            item.mkdirs();
        }
        List<File> files =  FileUtil.listFile(item);
        for(File file :files){
            try {
                String s = FileUtil.readFile2Str(file);
                List<ItemServerBean> itemList =new ArrayList<ItemServerBean>();
                itemList = JSON.parseArray(s,
                        ItemServerBean.class);
                ItemServerBean[] beanAry = new ItemServerBean[45];
                for(ItemServerBean itemServerBean : itemList){
                    beanAry[itemServerBean.getPosition()]=itemServerBean;
                }
                serverContext.itemArrayMap.put(Integer.valueOf(file.getName()),beanAry);
                // itemsMap.put(Integer.valueOf(file.getName()),itemList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public boolean  addItemToSomeOneFromWorld(int userId,int itemId){
        ItemServerBean item = getWorldItemById(itemId);
        if(item!=null) {

            ItemServerBean[] itemAry = getItemAryUserId(userId);

            if (itemAry != null) {
                for (int i = 0; i < itemAry.length; i++) {
                    if (itemAry[i] == null) {
                        itemAry[i] = item;
                        item.setPosition(i);
                        removeWorldItem(item);
                        return true;

                    }
                }
            }
        }
        return false;
    }
    public ItemServerBean[] getItemAryUserId(Integer id){

        ItemServerBean[] ary = serverContext.itemArrayMap.get(id);

        return  ary;

    }

    public ItemServerBean getWorldItemById(Integer id){

        for(ItemServerBean item:serverContext.worldItem){
            if(item.getId()== id ){
                return item;
            }
        }

        return  null;

    }


    public List<ItemServerBean> getItemByUserId(Integer id){
        List<ItemServerBean> list = new ArrayList<>();
        ItemServerBean[] ary = serverContext.itemArrayMap.get(id);
        if(ary!=null)
        for(ItemServerBean itemServerBean :ary){
            if(itemServerBean!=null){
                list.add(itemServerBean);
            }
        }
        return  list;

    }

    public void save(int userId, List<ItemServerBean > items){
        //添加新人员物品


        File file2 = PathManager.getInstance().getHomePath().resolve("saves").resolve("item").resolve(userId+"").toFile();
        try {
            FileUtil.writeFile(file2, JSON.toJSONString(items));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void addWorldItem(ItemServerBean item){
        serverContext.worldItem.add(item);
    }
    public void removeWorldItem(ItemServerBean item){
        serverContext.worldItem.remove(item);
    }
}
