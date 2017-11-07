package com.dozenx.game.network.server.service.impl;

import cola.machine.game.myblocks.engine.paths.PathManager;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
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
                serverContext.userId2ItemArrayMap.put(Integer.valueOf(file.getName()),beanAry);
                // itemsMap.put(Integer.valueOf(file.getName()),itemList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 将世界掉落的物品捡取到背包中
     * @param userId
     * @param itemId
     * @return
     */
    public boolean  addItemToSomeOneFromWorld(int userId,int itemId){
        ItemServerBean item = getWorldItemById(itemId);//查找
        if(item!=null) {//世界掉落物品中有此物品

            ItemServerBean[] itemAry = getItemAryUserId(userId);//拿到任务的当前背包
            ItemDefinition itemDefinition = ItemManager.getItemDefinition(item.getItemType());//获取最大叠加数目
            int stackNum = itemDefinition.stackNum;
            if (itemAry != null) {//如果查不到这个人的背包系统不予处理
                int emptySlotIndex=-1;//第一个空位
                for (int i = 0; i < itemAry.length; i++) {//遍历此人的包裹系统
                    if (emptySlotIndex==-1 && itemAry[i] == null ) {//如果还没发现空的slot 如果当前包裹的遍历到的slot是空的
                        emptySlotIndex=i;//记录
                    }

                    if (itemAry[i] != null&&itemAry[i].getItemType() == item.getItemType()) {//如果当前物品和背包中的物品是一个类型的叠加他们

                        //判断叠加数目是否已经满了
                        if(itemAry[i].getNum()+item.getNum()<stackNum){
                            itemAry[i].setNum(itemAry[i].getNum()+1);//物品数+1
                            removeWorldItem(item);//移除世界物品
                        }else{
                            continue;//如果已经满了 找下一个slot
                        }



                        //获取itemDefition

                        return true;

                    }
                }
                if(emptySlotIndex!=-1){//找一个空位放下
                    itemAry[emptySlotIndex] = item;
                    item.setPosition(emptySlotIndex);
                    removeWorldItem(item);//移除世界物品
                    removeWorldItem(item);
                }else{
                    //背包已经满了
                }

                return true;

            }
        }
        return false;
    }
    public ItemServerBean[] getItemAryUserId(Integer id){

        ItemServerBean[] ary = serverContext.userId2ItemArrayMap.get(id);

        return  ary;

    }

    public ItemServerBean[] getWorldItem(){

        ItemServerBean[] ary =new ItemServerBean[serverContext.worldItem.size()];
        return  serverContext.worldItem.toArray(ary);

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
        ItemServerBean[] ary = serverContext.userId2ItemArrayMap.get(id);
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
