package cola.machine.game.myblocks.model.ui.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Document {
	public static HashMap variables=new HashMap();
	public static List<HtmlObject> elements =new ArrayList<HtmlObject>(); 
	public static void appendChild(HtmlObject htmlObject){
		elements.add(htmlObject);
	}
	public void removeChild(HtmlObject htmlObject){
		elements.remove(htmlObject);
	}
	public static HtmlObject getElementById(String id){


		for(HtmlObject htmlObject : elements){


            /*if(id.equals(htmlObject.id)){
				return htmlObject;
			}else {*/
                HtmlObject childObject =htmlObject.getElementById(id);
                if(childObject!=null){
                    return childObject;
               }
           /* }*/

		}
		return null;
	}
	public static  Object var(String name){
		return variables.get(name);
	}
	public static void var(String name,Object value){
		 variables.put(name, value);
	}

}
