package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.model.base.ColorBlock;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.util.MapUtil;

public class LightBlock extends ColorBlock {
    public String toString(){
        StringBuffer buffer =new StringBuffer();
        buffer.append("{")
                .append("blocktype:'lightblock',")
                .append(toBaseBlockString())
                .append(toColorBlockString())

                .append("}");
        /*StringBuffer sb = new StringBuffer();
        sb.append(this.x).append(",").append(this.y).append(",").append(this.z).append(",")
                .append(this.width).append(",").append(this.height).append(",").append(this.thick).append(",")
                .append(this.rf).append(",").append(this.gf).append(",").append(this.bf);
        return sb.toString();*/
        return buffer.toString();
    }


    public static LightBlock parse(JSONObject map){

        LightBlock block =new LightBlock();

        block. parseColor(block,map);




        return block;


    }
    public  void parseColor(ColorBlock block ,JSONObject map){
        parse(block,map);

        float red = MapUtil.getFloatValue(map,"r");
        float green = MapUtil.getFloatValue(map,"g");
        float blue = MapUtil.getFloatValue(map,"b");
        float alpha = MapUtil.getFloatValue(map,"a");

        block.rf= red;
        block.gf= green;
        block.bf= blue;

        block.opacity = alpha;


    }
}
