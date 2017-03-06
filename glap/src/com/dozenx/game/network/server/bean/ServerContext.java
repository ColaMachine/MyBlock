package com.dozenx.game.network.server.bean;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dozenx.game.engine.command.CmdType;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.Worker;
import com.dozenx.game.network.server.handler.GameServerHandler;
import com.dozenx.util.FileUtil;
import core.log.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by dozen.zhang on 2017/2/21.
 */
public class ServerContext {
    private List<LivingThingBean> enemyList = new ArrayList<>();
    private List<LivingThingBean> onLinePlayer =  new ArrayList<>();
    private List<LivingThingBean> allPlayer =  new ArrayList<>();
    private Map<Integer,List<ItemServerBean>> itemsMap =  new HashMap<Integer,List<ItemServerBean>>();

    public List<Worker> getWorkers() {
        return workers;
    }

    public void addWorker(Worker worker){
        this.workers.add(worker);
    }

    /* public  Map<String,PlayerStatus> id2EnemyMap  =new HashMap();
        public  Map<String,PlayerStatus> name2PlayerMap  =new HashMap();
        public  Map<Integer,PlayerStatus> id2PlayerMap  =new HashMap();*/
   private  Map<CmdType,GameServerHandler> allHandlerMap =new HashMap<>();
    //public HashMap<String ,PlayerStatus> name2InfoMap =new HashMap();
    public List<LivingThingBean> getAllOnlinePlayer(){
        return onLinePlayer;
    }
    public void registerHandler(CmdType type,GameServerHandler handler ){
        allHandlerMap.put(type,handler);
    }
      public GameServerHandler getHandler(CmdType type){
          return allHandlerMap.get(type);
      }
    public List<LivingThingBean> getAllEnemies(){
        return enemyList;
    }
    public ServerContext(){
        loadAllUserInfo();
        loadAllEnemy();
    }
    //public HashMap<Integer , Socket> socketMap =new HashMap();
    private List<Worker> workers = new ArrayList<>( );
    //public Map<Integer,Worker> workerMap =new Hashtable();
    private Queue<byte[]> messages=new LinkedList<>();
  //  public Queue<PlayerStatus> livingThings=new LinkedList<>();
    public LivingThingBean addNewPlayer(PlayerStatus info){
        info.setIsplayer(true);
        File file = PathManager.getInstance().getHomePath().resolve("saves").resolve("player").resolve(info.getId()+".txt").toFile();
        try {
            FileUtil.writeFile(file, JSON.toJSONString(info));
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<ItemServerBean> items= new ArrayList<>();
        ItemServerBean item =new ItemServerBean();
        item.setId(1);
        item.setItemType(ItemType.arrow.ordinal());
        items.add(item);
        File file2 = PathManager.getInstance().getHomePath().resolve("saves").resolve("item").resolve(info.getId()+"").toFile();
        try {
            FileUtil.writeFile(file2, JSON.toJSONString(items));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LivingThingBean playerBean=new LivingThingBean();
        playerBean.setInfo(info);
        return playerBean;
    }
    public void loadAllEnemy(){
        File player =PathManager.getInstance().getHomePath().resolve("saves").resolve("enemy").toFile();
        if(!player.exists()){
            player.mkdirs();
        }
        List<File> files =  FileUtil.listFile(player);
        for(File file :files){
            try {
                String s = FileUtil.readFile2Str(file);
                LivingThingBean livingThingBean =new LivingThingBean();
                livingThingBean.setInfo(JSON.parseObject(s,
                        new TypeReference<PlayerStatus>() {
                        }));
                enemyList.add(livingThingBean);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void loadItems(){
        File item =PathManager.getInstance().getHomePath().resolve("saves").resolve("item").toFile();
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

                itemsMap.put(Integer.valueOf(item.getName()),itemList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void loadAllUserInfo(){
        File player =PathManager.getInstance().getHomePath().resolve("saves").resolve("player").toFile();
        if(!player.exists()){
            player.mkdirs();
        }
        List<File> files =  FileUtil.listFile(player);
        for(File file :files){
            try {
                String s = FileUtil.readFile2Str(file);
                LivingThingBean livingThingBean =new LivingThingBean();
                livingThingBean.setInfo(JSON.parseObject(s,
                        new TypeReference<PlayerStatus>() {
                        }));
                allPlayer.add(livingThingBean);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public List<ItemServerBean> getItemByUserId(Integer id){
        return  itemsMap.get(id);

    }
    public LivingThingBean getAllPlayerByName(String name){
        for(LivingThingBean player : allPlayer){
            if(name.equals(player.getName())){

                return player;
            }
        }
        return null;
    }
    public  LivingThingBean  getOnlinePlayerById(int id ){
        for(LivingThingBean player : onLinePlayer){
            if(player.getId() == id){

                return player;
            }
        }
        return null;

    }
    public  LivingThingBean  getEnemyById(int id ){
        for(LivingThingBean player : enemyList){
            if(player.getId() == id){

                return player;
            }
        }
        return null;

    }
    public void addOnlinePlayer(PlayerStatus status){
        for(LivingThingBean player : onLinePlayer){
            if(player.getId() == status.getId()){
                LogUtil.println("player:"+status.getId()+"already exist in online list");
                return;
            }
        }
        LivingThingBean livingThingBean=new LivingThingBean();
        livingThingBean.setInfo(status);
        onLinePlayer.add(livingThingBean);
    }

    public void removeOnlinePlayer(int id ){
        for(int i=onLinePlayer.size()-1;i>=0;i--){
            LivingThingBean player = onLinePlayer.get(i);
            if(player.getId()==id){
                synchronized (onLinePlayer) {
                    onLinePlayer.remove(i);
                    break;
                }
            }
        }
    }

   /* public void removeLivingThing(PlayerStatus status){
        id2PlayerMap.remove(status.getId());
    }*/

    public Queue<byte[]> getMessages() {
        return messages;
    }

    public void setMessages(Queue<byte[]> messages) {
        this.messages = messages;
    }



    public Map<CmdType, GameServerHandler> getAllHandlerMap() {
        return allHandlerMap;

    }

    public void setAllHandlerMap(HashMap<CmdType, GameServerHandler> allHandlerMap) {
        this.allHandlerMap = allHandlerMap;
    }

   /* public Map<String, PlayerStatus> getName2PlayerMap() {

        return name2PlayerMap;
    }

    public void setName2PlayerMap(Map<String, PlayerStatus> name2PlayerMap) {
        this.name2PlayerMap = name2PlayerMap;
    }

    public Map<Integer, PlayerStatus> getId2PlayerMap() {
        return id2PlayerMap;
    }

    public void setId2PlayerMap(Map<Integer, PlayerStatus> id2PlayerMap) {
        this.id2PlayerMap = id2PlayerMap;
    }
*/
    /*public Map<Integer, Worker> getWorkerMap() {
        return workerMap;
    }

    public void setWorkerMap(Map<Integer, Worker> workerMap) {
        this.workerMap = workerMap;
    }

*/

    public void broadCast(byte[] bytes){
        this.messages.offer(bytes);
    }
}
