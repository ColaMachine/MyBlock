package com.dozenx.game.network.server.bean;

import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.persistence.impl.StorageManagerInternal;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.chunks.ServerChunkProvider;
import cola.machine.game.myblocks.world.generator.WorldGenerators.PerlinWorldGenerator;
import com.dozenx.game.engine.command.CmdType;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.Worker;
import com.dozenx.game.network.server.handler.GameServerHandler;
import com.dozenx.game.network.server.service.GameServerService;

import java.util.*;

/**
 * Created by dozen.zhang on 2017/2/21.
 */
public class ServerContext {
    public List<LivingThingBean> enemyList = new ArrayList<>();
    public List<LivingThingBean> deadEnemyList = new ArrayList<>();
    public List<LivingThingBean> onLinePlayer =  new ArrayList<>();
    public List<LivingThingBean> allPlayer =  new ArrayList<>();
    //private Map<Integer,List<ItemServerBean>> itemsMap =  new HashMap<Integer,List<ItemServerBean>>();
    //the userId or the box id to itemserverbean array 用户id 对应物品数组  包括箱子
    public Map<Integer,ItemServerBean[]> userId2ItemArrayMap =  new HashMap<Integer,ItemServerBean[]>();
    public List<ItemServerBean> worldItem = new ArrayList<>();
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

    private Map<Class,GameServerService> allServiceMap =new HashMap<>();
    //public HashMap<String ,PlayerStatus> name2InfoMap =new HashMap();

    public void registerHandler(CmdType type,GameServerHandler handler ){
        allHandlerMap.put(type,handler);
    }

    public GameServerService getService(Class t){
        return allServiceMap.get(t);
    }
    public void registerService(Class t,GameServerService serverService ){
        allServiceMap.put(t,serverService);
    }
      public GameServerHandler getHandler(CmdType type){
          return allHandlerMap.get(type);
      }
 /*   public List<LivingThingBean> getAllEnemies(){
        return enemyList;
    }*/
    public ServerContext(){
      /*  loadAllUserInfo();
        loadAllEnemy();
        loadItems();*/


        StorageManager storageManager = new StorageManagerInternal();
        PerlinWorldGenerator worldGenerator = new PerlinWorldGenerator();
        worldGenerator.initialize();
        worldGenerator.setWorldSeed("123123123");
         chunkProvider = new ServerChunkProvider(storageManager, worldGenerator);

    }
    public GeneratingChunkProvider chunkProvider;
    //public HashMap<Integer , Socket> socketMap =new HashMap();
    private List<Worker> workers = new ArrayList<>( );
    //public Map<Integer,Worker> workerMap =new Hashtable();
    private Queue<byte[]> messages=new LinkedList<>();
  //  public Queue<PlayerStatus> livingThings=new LinkedList<>();
  /*  public LivingThingBean addNewPlayer(PlayerStatus info){
        info.setIsplayer(true);
        File file = PathManager.getInstance().getHomePath().resolve("saves").resolve("player").resolve(info.getId()+".txt").toFile();
        try {
            FileUtil.writeFile(file, JSON.toJSONString(info));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //添加新人员物品
        List<ItemServerBean> items= new ArrayList<>();
        ItemServerBean item =new ItemServerBean();
        item.setId(1);
        item.setItemType(ItemType.arrow.ordinal());
        items.add(item);
        item.setPosition(0);
        File file2 = PathManager.getInstance().getHomePath().resolve("saves").resolve("item").resolve(info.getId()+"").toFile();
        try {
            FileUtil.writeFile(file2, JSON.toJSONString(items));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ItemServerBean[] itemServerBeenAry =new ItemServerBean[25];
        itemServerBeenAry[0]=item;

        itemArrayMap.put(info.getId(),itemServerBeenAry);

        LivingThingBean playerBean=new LivingThingBean();
        playerBean.setInfo(info);
        return playerBean;
    }*/



/*
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


    }*//*
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
                ItemServerBean[] beanAry = new ItemServerBean[45];
                for(ItemServerBean itemServerBean : itemList){
                    beanAry[itemServerBean.getPosition()]=itemServerBean;
                }
                itemArrayMap.put(Integer.valueOf(file.getName()),beanAry);
                // itemsMap.put(Integer.valueOf(file.getName()),itemList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public ItemServerBean[] getItemAryUserId(Integer id){

        ItemServerBean[] ary = itemArrayMap.get(id);

        return  ary;

    }
    public List<ItemServerBean> getItemByUserId(Integer id){
        List<ItemServerBean> list = new ArrayList<>();
        ItemServerBean[] ary = itemArrayMap.get(id);
        for(ItemServerBean itemServerBean :ary){
            if(itemServerBean!=null){
                list.add(itemServerBean);
            }
        }
        return  list;

    }*/
  /*  public LivingThingBean getAllPlayerByName(String name){
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

    }*/
    /*public  LivingThingBean  getEnemyById(int id ){
        for(LivingThingBean player : enemyList){
            if(player.getId() == id){

                return player;
            }
        }
        return null;

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


    }*/
  /*  public void addOnlinePlayer(PlayerStatus status){
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
    }*/

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
