package com.dozenx.game.engine.ui.inventory.control;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.BoxBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.engine.ui.inventory.BoxService.BoxClientService;

import java.util.List;

/**
 * 容器 箱子
 * container like box
 * Created by luying on 16/7/3.
 */
public class BoxController {
     /*  private BagEntity bag;*/

    private BoxClientService boxClientService =new BoxClientService();
    private ItemBean[] itemBeans = new ItemBean[20];
    Player player;
    public BoxController(Player player){
        this.player =player;
        CoreRegistry.put(BoxController.class,this);
    }

    /**
     * 创建一个箱子
     * @param worldx
     * @param worldy
     * @param worldz
     */
    public void create(int worldx,int worldy,int worldz){

    }

    /**
     * 塞入物体
     * @param worldx
     * @param worldy
     * @param worldz
     * @param slotIndex
     * @param itemBean
     */
    /*public void putin(int worldx,int worldy,int worldz,int slotIndex,ItemBean itemBean){
        boxClientService.putIn(currentBox,slotIndex,itemBean);
    }*/
    private Integer nowBoxX,nowBoxY,nowBoxZ;
    BaseBlock currentBox;

    /**
     * 返回人物现在选定的箱子的物体列表
     * @return
     */
    public ItemBean[] getNowItemBeans(){
        return itemBeans;
    }
    public int getNowBoxId(){
            if(currentBox==null){
                return 0;
            }
          int chunkX = currentBox.getChunk().chunkPos.x;


        int chunkZ= currentBox.getChunk().chunkPos.z;
        int x = currentBox.getX();
        int y = currentBox.getY();
        int z = currentBox.getZ();
        int boxId = (chunkX*16 +x)*100000+(chunkZ*16+z)*1000+y;
        return boxId;
    }
    /**
     * 打开制定箱子并获取物品列表数据
     * @param boxBlock
     * @return
     */
    public ItemBean[] openBox( BaseBlock boxBlock,int chunkX,int chunkZ,int cx,int cy,int cz){
        //=========记录当前box==========
        nowBoxX=boxBlock.getX();
        nowBoxY=boxBlock.getY();
        nowBoxZ =boxBlock.getZ();
        currentBox = boxBlock;
        //========修改当前box属性=========
       // boxBlock.open=1;
        List<ItemServerBean> itemBeanList = boxClientService.openAndGetItemBeanList(boxBlock,chunkX, chunkZ, cx, cy, cz);

        for(int i=0,length=itemBeans.length;i<length;i++){
            itemBeans[i]=null;
        }
        for(int i=0;i<itemBeanList.size();i++){
            ItemServerBean itemServerBean = itemBeanList.get(i);

            ItemBean itemBean =new ItemBean();
            itemBean.setPosition(itemServerBean.getPosition());
            itemBean.setNum(itemServerBean.getNum());
            itemBean.setItemDefinition(ItemManager.getItemDefinition(itemServerBean.getItemType()));
            itemBean.setId(0);

            itemBeans[itemBean.getPosition()-35] = itemBean;
            //slot[itemServerBean.getPosition()-35].setIconView(new IconView(itemBean));

        }


        return  itemBeans;
    }
    /**
     * 取出物体
     * @param worldx
     * @param worldy
     * @param worldz
     * @param slotIndex
     * @param itemBean
     */
    public void getout(int worldx,int worldy,int worldz,int slotIndex,ItemBean itemBean){

    }

    /**
     * 扔掉物体
     * @param worldx
     * @param worldy
     * @param worldz
     * @param slotIndex
     * @param itemBean
     */
    public void drop(int worldx,int worldy,int worldz,int slotIndex,ItemBean itemBean){

    }

    /**
     * 内部交换
     * @param worldx
     * @param worldy
     * @param worldz
     * @param fromslotIndex
     * @param fromItemBean
     * @param toSlotIndex
     * @param toItemBean
     */
    public void changeinner(int worldx,int worldy,int worldz,int fromslotIndex,ItemBean fromItemBean,int toSlotIndex,ItemBean toItemBean){

    }

    /**
     * 和任务的背包容器进行交换
     * @param worldx
     * @param worldy
     * @param worldz
     * @param fromslotIndex
     * @param fromItemBean
     * @param toSlotIndex
     * @param toItemBean
     */
    public void changeoutinner(int worldx,int worldy,int worldz,int fromslotIndex,ItemBean fromItemBean,int toSlotIndex,ItemBean toItemBean){

    }


}
