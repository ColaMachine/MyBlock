package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.item.ItemManager;
import cola.machine.game.myblocks.model.textture.Shape;
import com.alibaba.fastjson.JSON;
import com.dozenx.util.FileUtil;
import util.MapUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by luying on 16/9/25.
 */
public class SkillManager {
    public static HashMap<String,SkillDefinition> definitionMap = new HashMap<>();
    //public List<SkillEntity> skil
    public SkillEntity currentSkill ;
    public Long startTime ;
    public void update(){

    }
    public void render(){

    }
    public void loadSkillDefinition()throws Exception{

        try  {
            String json = FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("config/skill.cfg").toString()) ;
            List<HashMap> definitions=  JSON.parseArray(json, HashMap.class);
            for(int i=0;i<definitions.size();i++) {
                HashMap map = definitions.get(i);
                SkillDefinition definition = new SkillDefinition();
                String name = (String) map.get("name");
                definition.setName(name);
                String desc = (String) map.get("desc");
                definition.setDesc(desc);
                String showName = (String) map.get("showName");
                definition.setShowName(showName);
                String type = MapUtil.getStringValue(map, "type");
                if (type.equals("damage")) {
                    definition.setType(Constants.SKILL_TYPE_DAMAGE);
                } else if (type.equals("heal")) {
                    definition.setType(Constants.SKILL_TYPE_HEAL);
                }

                String damageType = MapUtil.getStringValue(map, "damageType");
                if (damageType.equals("physic")) {
                    definition.setDamageType(Constants.SKILL_DAMAGE_TYPE_PHYSIC);
                } else if (damageType.equals("magic")) {
                    definition.setDamageType(Constants.SKILL_DAMAGE_TYPE_MAGIC);
                }

                int damageValue = MapUtil.getIntValue(map, "damageValue");
                definition.setDamageValue(damageValue);
                int castTime = MapUtil.getIntValue(map, "castTime");
                definition.setCastTime(castTime);

                int coolDown = MapUtil.getIntValue(map, "coolDown");
                definition.setCoolDown(coolDown);
                definitionMap.put(definition.getName(),definition);
            }


        } catch (Exception e) {
            throw new Exception("Failed to load config", e);
        }

    }

    public static SkillDefinition get(String definitionName){
        return definitionMap.get(definitionName);
    }

}
