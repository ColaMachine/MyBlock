package com.dozenx.util;


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

	public static boolean  getBooleanValue(Map map , String name,Boolean defaultValue){
		Object object = map.get(name);
if(object == null ){
	return defaultValue;
}

		return Boolean.valueOf(""+object);
	}


	public static Float getFloatValue(Map map , String name,Float defalutValue){
		Object object = map.get(name);
		if(object!=null) {
			if (object instanceof Integer) {
				Float value = Float.valueOf((Integer) object);
				return value;
			}
			if (object instanceof BigDecimal) {
				Float value = Float.valueOf(((BigDecimal) object).floatValue());
				return value;
			}
		}else{
			return defalutValue;
		}
		Float value =(Float)map.get(name);
		return value;
	}
	public static String getStringValue(Map map , String name){
        Object obj = map.get(name);
        if(obj==null){
            return null;
        }

		return obj.toString();
	}
	public static Integer getIntValue(Map map , String name){
		Object object =map.get(name);
		if(object == null){
			return null;
		}
		Integer value =(Integer)object;
		return value;
	}
	
}
