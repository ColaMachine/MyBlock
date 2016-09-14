package util;


import java.math.BigDecimal;
import java.util.Map;


public class MapUtil {

	public static Float getFloatValue(Map map , String name){
		Object object = map.get(name);
		if(object instanceof  Integer){
			Float value =Float.valueOf((Integer)object);
			return value;
		}
		if(object instanceof BigDecimal){
			Float value =Float.valueOf(((BigDecimal)object).floatValue());
			return value;
		}
		Float value =(Float)map.get(name);
		return value;
	}
	public static String getStringValue(Map map , String name){
		String value =map.get(name)+"";
		return value;
	}
	public static Integer getIntValue(Map map , String name){
		Integer value =(Integer)map.get(name);
		return value;
	}
	
}
