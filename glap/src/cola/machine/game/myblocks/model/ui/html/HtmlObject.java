package cola.machine.game.myblocks.model.ui.html;

import de.matthiasmann.twl.*;
import cola.machine.game.myblocks.engine.paths.PathManager;
//import cola.machine.game.myblocks.input.Event;
import cola.machine.game.myblocks.input.KeyEventReceiver;
import cola.machine.game.myblocks.input.MouseEventReceiver;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.StringUtil;
import de.matthiasmann.twl.Event;
import glapp.GLApp;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.Doc;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by luying on 14-9-17.
 */
public class HtmlObject implements Cloneable  {


    private static final int LAYOUT_INVALID_LOCAL  = 1;
    private static final int LAYOUT_INVALID_GLOBAL = 3;

    private static final Logger logger = LoggerFactory.getLogger(HtmlObject.class);
    public MouseEventReceiver mouseEventReceiver;
    public KeyEventReceiver keyEventReceiver;
    public  HtmlObject parentNode;
    public   List<HtmlObject> childNodes =new ArrayList<HtmlObject>();
    public float index=0;

    public int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public static int POSITION_ABSOLUTE=1;

    public static int POSITION_RELATIVE=2;

    public static int POSITION_STATIC=0;
    /**id**/
    public String id;
    /**name**/
    public  String name;
    /**text**/

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    private  float fontSize=12;

    public float getFontSize() {
        return fontSize;
    }

    public float getEffectFontSize() {
        float _fontSize =0 ;
        if(this.fontSize==0){
            if(parentNode!=null){
                _fontSize= parentNode.getEffectFontSize();
            }

        }else{
            _fontSize= fontSize;
        }
        if(_fontSize==0){
            _fontSize=12;
        }
        return _fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public String innerText;
    /**position x**/
    private int posX;
    /**position y**/
    private int posY;
    /** the outer width**/
    protected  int width;
    /** the outer height**/
    protected  int height;
    /**disabled**/
    private boolean disabled = false;
    volatile Document document;
    private PropertyChangeSupport propertyChangeSupport;

    private short borderLeft;
    private short borderTop;
    private short borderRight;
    private short borderBottom;

    short offsetLeft =0;
    short offsetTop=0;
    short offsetWidth=0;
    short offsetHeight=0;

    private short minWidth;
    private short minHeight;
    private short maxWidth;
    private short maxHeight;

    private short marginTop;
    private short marginLeft;
    private short marginBottom;
    private short marginRight;

    private short paddingTop;
    private short paddingLeft;
    private short paddingBottom;
    private short paddingRight;

    private  int top;
    private  int left;


    private int layoutInvalid;

    private  Vector4f backgroundColor;

    //public float top;
    private boolean absolute=false;
    private boolean relative=false;
    private boolean visible =true;

    //public String display;
    //public  float offsetleft;
    //public  float OffsetRight;
    private  Image backgroundImage;


    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Vector4f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Vector4f getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Vector4f borderColor) {
        this.borderColor = borderColor;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;

    }
    public HtmlObject getWidgetAt(int x, int y) {
        HtmlObject child = getChildAt(x, y);
        if(child != null) {//child 不为空 递归找到最下面的widget
            return child.getWidgetAt(x, y);
        }
        return this;
    }
    public HtmlObject getParent() {
        return parentNode;
    }
    public HtmlObject getParentNode() {
        return parentNode;
    }

    public void setParentNode(HtmlObject parentNode) {
        this.parentNode = parentNode;
    }

    public Vector4f borderColor;
    public void onClick(){

    }
    public void appendChild(HtmlObject htmlObject){
        if(htmlObject==null){
            return;
        }
        if(parentNode!=null){
            parentNode.removeChild(htmlObject);
        }

        childNodes.add(htmlObject);htmlObject.parentNode=this;
        //htmlObject.document=parentNode.document;
    }
    public void removeAllChild(){
        while(this.childNodes!=null && this.childNodes.size()>0)
            this.childNodes.remove(this.childNodes.size()-1);

    }
    public void removeChild(){
        if(this.childNodes!=null && this.childNodes.size()>0)
        this.childNodes.remove(this.childNodes.size()-1);

    }
    public void removeChild(HtmlObject htmlObject){
        this.childNodes.remove(htmlObject);
    }

    public void removeChild(int i){
        if(this.childNodes!=null && this.childNodes.size()>i-1)
            this.childNodes.remove(i);

    }
    public int getWidth (){

        if(width>0){
            return width;
        }else{
            if(parentNode==null){
                LogUtil.println("parentNode==null");
            }
            try {
                setWidth(parentNode.getWidth());///this.parentNode.childNodes.size() because of tr's width  may be the whole with of table
            }catch (Exception e){
                e.printStackTrace();
            }
            return width;
        }
    }
    public int getHeight(){
        if(this.height>0){
            return  height;
        }else{
            return this.parentNode. getHeight();
        }


    }
    public int getLeft(){
        return this.left;
    }

    public float getRecurLeft(){
        if(this.getParentNode()!=null){
            return  this.getParentNode().getRecurLeft()+ this.getOffsetLeft();
        }else{
            return this.getOffsetLeft();
        }
    }
    public float getRecurTop(){
        if(this.getParentNode()!=null){
            return  this.getParentNode().getRecurTop()+ this.getOffsetTop();
        }else{
            return this.getOffsetTop();
        }
    }
    public float getOffsetLeft(){
        return this.offsetLeft;
        //if set the left
        //then the left = left + parent.minx
        ///else judge the
        /*if(this.left>0)
        return left;
        else
        {
            if(this.parentNode!=null)
                return this.parentNode.getLeft();
            else
                return 0;
        }*/




       /* if(this.parentNode!=null) {
        int index = this.getParentNode().childNodes.indexOf(this);
            if(index==0) {
                return this.parentNode.getInnerY()+ this.marginLeft;
            }else{

                    HtmlObject prevNode =  this.getParentNode().getChildNodes().get(index-1);

                if(this.getParentNode().getInnerWidth()>0 && this.getParentNode().getInnerWidth()< prevNode.getRight() - this.getParentNode().childNodes.get(0).posX){
                    //this.parentNode.width= prevNode.getRight()+prevNode.getMarginRight()+ this.parentNode.getBorderRight()- this.parentNode.posX;

                    return this.parentNode.getInnerY()+ this.marginLeft;
                }

                return prevNode.getRight()+ this.marginLeft;
            }

        }
        else{
            return 0;
        }*/
    }

    public int getTop(){

            return this.top;

    }
    public List<HtmlObject> getChildNodes(){
        return this.childNodes;
    }
    public float getOffsetTop(){

       /* if(this.parentNode!=null) {
            int index = this.getParentNode().childNodes.indexOf(this);
            if(index==0) {
                //return this.parentNode.getOffsetTop()+this.parent + this.marginTop;
                return this.getParentNode().getInnerY()+this.getMarginTop();
            }else{
                HtmlObject prevNode =  this.getParentNode().getChildNodes().get(index-1);
                //return prevNode.getOffsetTop()+prevNode.getHeight()+ this.marginTop;
                //return prevNode.getBottom()+prevNode.getHeight()+ this.marginTop;
                //如果出现了换行的情况
                if(this.getParentNode().getInnerWidth()>0 && this.getParentNode().getInnerWidth()< prevNode.getRight() - this.getParentNode().childNodes.get(0).posX){
                   // this.parentNode.height= prevNode.getBottom()+this.getMarginTop()+ this.parentNode.getBorderRight()- this.parentNode.posX;
                    return prevNode.getBottom()+this.getMarginTop();
                }
                return this.getParentNode().getInnerY()+this.getMarginTop();
            }

        }
        else{
            return 0;
        }*/
        return this.offsetTop;
        /*
        if(this.parentNode!=null)
            return this.parentNode.getOffsetTop()+this.top;
        else{
            return this.top;
        }*/
    }

    public int getOffsetBottom(){
        if(this.parentNode!=null)
            return (int)this.getOffsetTop()+this.getHeight();
        else{
            return this.top+this.getHeight();
        }
    }
    /*public void reSize(){
        this.minX=this.parentNode.minX;
        this.minY=this.parentNode.minY;
        this.maxX=this.parentNode.maxX;
        this.maxY=this.parentNode.maxY;
        //ShaderUtils.create2dimageVao(vao,minX,minY,maxX-minX,maxY-minY);
        //ShaderUtils.createBorderVao(borderVao,minX,minY,maxX-minX,maxY-minY);
    }*/
    public static final int INLINE=0;
    public static final int BLOCK=1;
    public int display;

    //int minHeight;
    //int minWidth;

    public HtmlObject  getPrevNode(){
        if(this.getParentNode()!=null){
            int index=0;
            if(this.getParentNode().getChildNodes().size()>0 && (index=this.getParentNode().getChildNodes().indexOf(this))>0){
                return this.getParentNode().getChildNodes().get(index-1);
            }
            return null;
        }else{
            return null;
        }
    }
    public void resize(){

        for (int i = 0; i < this.childNodes.size(); i++) {
            //System.out.println("2div id:"+id);
            HtmlObject child =  this.childNodes.get(i);
            if("bloodBar".equals(child.getId())){
//                LogUtil.println(")");
            }
            child.resize();
        }
        int maxWidth=0;
        int sumWidth =0;
        int sumHeight =0;
        int maxHeight =0;
        //计算宽度

        //HtmlObject prevNode =this.getPrevNode();
        //如果宽度是自适应的话
        if(StringUtil.isNotEmpty(this.innerText)){
            maxWidth=(int)(this.innerText.length()*this.getFontSize());
            maxHeight=(int)(this.getFontSize()*1.5);
        }




        HtmlObject oldChild=null;
            for (int i = 0; i < this.childNodes.size(); i++) {
                //System.out.println("2div id:"+id);
                HtmlObject child =  this.childNodes.get(i);
                //child.offsetWidth= (short)Math.max(width,minWidth);
                //child.offsetHeight= (short)Math.max(width,minWidth);
                if("bloodBar".equals(child.getId())){
                   // LogUtil.println(")");
                }
                int parentLeft = 0;
                int parentTop=0;
                if(getParentNode()!=null ){
                    parentLeft= this.getBorderLeft()+this.getPaddingLeft();
                    parentTop= this.getBorderTop()+this.getPaddingTop();

                }
                if(child.position==HtmlObject.POSITION_ABSOLUTE){
                    child.offsetLeft =(short) child.left;
                    child.offsetTop = (short)child.top;
                }else
                if(child.display==INLINE){
                    if(oldChild!=null && oldChild.getPosition()!=HtmlObject.POSITION_ABSOLUTE ) {
                        if (oldChild.display == INLINE) {

                            child.offsetLeft = (short)(oldChild.offsetLeft + oldChild.offsetWidth  + oldChild.marginRight + child.marginLeft);
                            child.offsetTop = (short)(oldChild.offsetTop);

                            if(this.width>0&& child.offsetLeft+child.width>this.width){
                                child.offsetLeft= child.marginLeft;
                                child.offsetTop=(short)(oldChild.offsetTop+oldChild.height+child.marginTop);
                            }

                        }else if(oldChild.display == BLOCK){
                            child.offsetLeft = child.marginLeft;
                            child.offsetTop = (short)(oldChild.offsetTop+oldChild.height+child.marginTop);
                        }
                    }else{

                            child.offsetLeft = (short)(child.marginLeft+parentLeft);
                            child.offsetTop = (short)(child.marginTop+parentTop);


                        }
                }else if(child.display==BLOCK){

                        child.offsetLeft = (short)(child.marginLeft+parentLeft);


                    if(oldChild!=null ){
                        child.offsetTop = (short)(oldChild.offsetTop+oldChild.getHeight()+oldChild.getBorderBottom()+child.marginTop);
                    }else{
                        child.offsetTop = (short)(child.marginTop+parentTop);
                    }

                }
                maxWidth = Math.max(maxWidth, child.offsetWidth+child.offsetLeft+child.marginRight);
                maxHeight = Math.max(maxHeight, child.offsetHeight+child.offsetTop+child.marginBottom);

                //sumWidth+=child.getWidth()+child.getMarginLeft()+child.getMarginRight();
                oldChild=child;
                //sumWidth+=this.childNodes.get(i).getWidth()+this.childNodes.get(i).getMarginLeft()+this.childNodes.get(i).getMarginRight();

            }
        if(this.width==0) {
            this.offsetWidth = (short)(maxWidth + this.borderRight + this.paddingRight);
        }else{
            this.offsetWidth=(short)width;
        }
        if(this.height==0) {
            this.offsetHeight = (short)(maxHeight + this.borderBottom + this.paddingBottom);
        }else{
            this.offsetHeight=(short)height;
        }
        setWidth ( offsetWidth);
        setHeight(offsetHeight);

    }

    public void countTextArea(){
        if(StringUtil.isNotEmpty(this.innerText)){
            float textLength=this.innerText.length()*this.fontSize;
            float textHeight=this.fontSize;
        }

    }
    public void update(){
    	//this.resize();
    	/*if (this.parentNode == null) {
			this.minX = this.left;
			this.maxX = this.minX + this.width;
			this.minY = this.bottom;
			this.maxY = this.minY + this.height;

		} else {
			// if the parentnode has only one child
			// if(this.parentNode.childNodes.size()==1){
			this.minX = this.parentNode.minX + this.left;
			this.maxX = this.minX + this.width;
			this.minY = this.parentNode.minY + this.bottom;
			this.maxY = this.minY + this.height;
			// }else{
			// logger.error("the parentnode has child :"+this.parentNode.childNodes.size());
			// System.exit(0);
			// }
		}*/
       //int left = this.getLeft();

        /*int index = this.parent.childNodes.indexOf(this);
        if(index==0){
            this.getOffsetLeft();
        }*/
        this.posX= this.getParentNode()!=null ? this.getParentNode().posX+offsetLeft : offsetLeft;
        this.posY=this.getParentNode()!=null ? this.getParentNode().posY+offsetTop : offsetTop;

        //this.maxX=this.minX+this.getWidth();
        //this.maxY=this.minY+this.getHeight();
        //ShaderUtils.create2dimageVao(vao,minX,minY,this.getWidth(),this.getHeight());

        //ShaderUtils.create2dimageVao(vao,minX,minY,maxX-minX,maxY-minY);
        //ShaderUtils.createBorderVao(borderVao,minX,minY,maxX-minX,maxY-minY);

        if(this.getWidth()==0){
            logger.error("maxX can't not be 0");
            System.exit(0);
        }

        if(color ==null){
            if(this.getParent()!=null){
                color = this.getParent().getColor();
            }
        }
        if(color ==null){
            color= new Vector4f(0,0,0,1);
        }
        //先确定父级的左上角 在确定子的左上角 再确定子的宽度高度 再确定父亲的宽度高度
        for(int i=0;i<this.childNodes.size();i++){
        	//System.out.println("2div id:"+id);
            this.childNodes.get(i).update();
        }
       // this.width = this.getWidth()>this.getChildMaxRight()-this.;
        if(StringUtil.isNotEmpty(innerText)){

        }
    }
    private Vector4f color;

    public Vector4f getColor() {

        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public void check(){

        for(HtmlObject htmlObject:this.childNodes){
            //if(Switcher.SHADER_ENABLE){
            htmlObject.check();
            //}else{
            //    htmlObject.render();
            //}

        }

    }
    /*public int getChildMaxRight(){
      this.getBorderRight()
              this
    }*/
    //Vao vao =new Vao();
    //Vao borderVao =new Vao();
    public void  buildVao(){
        if(!visible)return;
        if("bloodBar".equals(this.getId())){
           // LogUtil.println(")");
        }
        if(this.getBackgroundColor()!=null){
            ShaderUtils.draw2dColor(this.getBackgroundColor(),getInnerX(),getInnerY(),this.index+0.004f,getInnerWidth(),getInnerHeight());   OpenglUtils.checkGLError();
            //ShaderUtils.draw2DColorWithShader(this.getBackgroundColor(),ShaderUtils.get2DColorShaderConfig(),vao);
        }
        if(this.getBackgroundImage()!=null){
            /*if(this.background_image.equals("toolbar")){
        		System.out.println("toolbar render");
        	}*/
            ShaderUtils.draw2dImg(this.getBackgroundImage(), getInnerX(),getInnerY(),this.index+0.003f,getInnerWidth(),getInnerHeight());   OpenglUtils.checkGLError();
            //ShaderUtils.draw2DImageWithShader(ShaderUtils.get2DImageShaderConfig(), vao);
            //OpenglUtils.draw2DImageShader(i);
            //OpenglUtils.draw2DImageShader(textureInfo.textureHandle,1,1,50,50);
        }

        if(this.innerText!=null && innerText.length()>0){
            //GL11.glColor4f(1, 1, 1, 1);
            ShaderUtils.printText(this.innerText,getInnerX(),getInnerY(),this.index+0.001f,this.getFontSize(),this.getColor());
            OpenglUtils.checkGLError();

            //GLApp.print((int)minX,(int)minY,this.innerText);
        }
        if(this.borderLeft>0){
            ShaderUtils.draw2dColor(this.getBorderColor(),posX,posY,index+0.004f,borderLeft,height);   OpenglUtils.checkGLError();
        }
        if(this.borderTop>0){
            ShaderUtils.draw2dColor(this.getBorderColor(),posX,posY,index+0.004f,width,borderTop);   OpenglUtils.checkGLError();
        }
        if(this.borderRight>0){
            ShaderUtils.draw2dColor(this.getBorderColor(),posX+width-borderRight,posY,index+0.004f,borderRight,height);   OpenglUtils.checkGLError();
        }
        if(this.borderBottom>0){
            ShaderUtils.draw2dColor(this.getBorderColor(),posX,posY+height-borderBottom,index+0.004f,width,borderBottom);   OpenglUtils.checkGLError();
        }

        for(HtmlObject htmlObject:this.childNodes){
            //if(Switcher.SHADER_ENABLE){
                htmlObject.buildVao();
            //}else{
            //    htmlObject.render();
            //}

        }

      /*  for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).render();
        }*/


    }

    public void giveupKeyboardFocus() {
        if(getParent() != null && getParent().focusChild == this) {
            getParent().requestKeyboardFocus(null);
        }
    }
    public void render(){
        if(!visible)return;
        if(this.getBackgroundImage()!=null){
        	/*if(this.background_image.equals("toolbar")){
        		System.out.println("toolbar render");
        	}*/


            TextureInfo textureInfo = this.getBackgroundImage().getTexture();


            //OpenglUtils.draw2DImageShader(textureInfo.textureHandle,1,1,50,50);
            GL11.glColor3f(1, 1, 1);
           // GL11.glClear(GL11.GL_COLOR);
            textureInfo.bind();
            //GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureInfo.textureHandle);

            GL11.glEnable(GL11.GL_TEXTURE_2D);   // be sure textures are on


            GL11.glBegin(GL11.GL_QUADS);

            GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z
            GL11.glTexCoord2f(textureInfo.minX, textureInfo.minY);
          //  GL11.glVertex3f(this.getLeft(), this.getBottom(), (float)-10);
            GL11.glVertex3f(getMinX(), getMinY(), (float)-10);
            GL11.glTexCoord2f(textureInfo.maxX, textureInfo.minY);
            //GL11.glVertex3f(this.getLeft()+this.getWidth(), this.getBottom(), (float)-10);
            GL11.glVertex3f(getMaxX(), getMinY(), (float)-10);
            GL11.glTexCoord2f(textureInfo.maxX, textureInfo.maxY);
            GL11.glVertex3f(getMaxX(), getMaxY(), (float)-10);
            GL11.glTexCoord2f(textureInfo.minX, textureInfo.maxY);
            GL11.glVertex3f(getMinX(), getMaxY(), (float)-10);
            GL11.glEnd();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }else if(this.getBackgroundColor()!=null){
            // turn off light
            //set color
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(this.getBackgroundColor().x,getBackgroundColor().y,getBackgroundColor().z,getBackgroundColor().w);
            //GL11.glColor4f(1f,0f,0f,0.8f);
            //GL11.glDisable(GL11.GL_LIGHT0);
            GL11.glEnable(GL11.GL_BLEND); // 打开混合
            GL11.glDisable(GL11.GL_DEPTH_TEST); // 关闭深度测试
            //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE); // 基于源象素alpha通道值的半透明混合函数

            GL11.glBegin(GL11.GL_QUADS);

            //GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z
            GL11.glVertex3f(getMinX(), getMinY(), (float) -10);
            GL11.glVertex3f(getMaxX(), getMinY(), (float) -10);
            GL11.glVertex3f(getMaxX(), getMaxY(), (float) -10);
            GL11.glVertex3f(getMinX(), getMaxY(), (float) -10);
            GL11.glEnd();
            GL11.glEnable(GL11.GL_LIGHT0);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        if(this.innerText!=null &&! innerText.equals("")){
            GL11.glColor4f(1, 1, 1, 1);
            GLApp.print((int)getMinX(),(int)getMinY(),this.innerText);
        }
      if(this.borderTop>0){
            GL11.glLineWidth(this.borderTop);
            GL11.glColor3f(this.getBorderColor().x,this.getBorderColor().y,this.getBorderColor().z);
            GLApp.drawRect((int)this.getMinX(),(int)this.getMinY(),this.getMaxX()-this.getMinX(),this.getMaxY()-this.getMinY());

        }
        for(HtmlObject htmlObject:this.childNodes){
            htmlObject.render();
        }

      /*  for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).render();
        }*/


    }

    public int getMinX(){
        return posX;
    }

    public int getMinY(){
        return posY;
    }
    public int getMaxX(){
        return posX+width;
    }
    public int getMaxY(){
        return posY+height;
    }
    public HtmlObject getElementById(String id){
        if(id.equals(this.id)){
            return this;
        }
        for(HtmlObject htmlObject:this.childNodes){
            HtmlObject childObject = htmlObject.getElementById(id);

            if(childObject!=null){
                return childObject;
            }
        }
        return null;
    }


    protected final HtmlObject getChildAt(int x, int y) {
        if(childNodes != null) {
            for(int i=childNodes.size(); i-->0 ;) {
                HtmlObject child = childNodes.get(i);
                if(child.visible && child.isInside(x, y)) {
                    return child;
                }
            }
        }
        return null;
    }
    public HtmlObject getDomAt(int x, int y) {
        HtmlObject child = getChildAt(x, y);
        if(child != null) {//child 不为空 递归找到最下面的widget
            return child.getDomAt(x, y);
        }
        return this;
    }

    public void onClick(Event event){
        if(!visible)
            return;

      /*  if(event.cancelBubble)
            return;*/

    	if(this.isInside(event.mouseX, event.mouseY)){
            if(this.mouseEventReceiver!=null) {//System.out.println("the clicked element id:"+this.id);
                this.mouseEventReceiver.mouseClick(event.mouseX, event.mouseY, this);
                //event.cancelBubble=true;
                Switcher.MOUSE_CANCELBUBLE=true;

                return;
            }else
    		/*for(HtmlObject htmlObject:this.childNodes){
    			htmlObject.onClick(event);
    		}*/
            for(int i=childNodes.size()-1;i>-1;i--){
                try {
                    childNodes.get(i).onClick(event);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
    	}



    }



    /**
     * Returns the right X coordinate of this widget
     * @return getX() + getWidth()
     */
    public final int getRight() {
        return posX + width;
    }

    /**
     * Returns the bottom Y coordinate of this widget
     * @return getY() + getHeight()
     */
    public final int getBottom() {
        return posY + height;
    }

    /**
     * The inner X position takes the left border into account
     * @return getX() + getBorderLeft()
     */
    public final int getInnerX() {
        return posX + borderLeft;
    }

    /**
     * The inner Y position takes the top border into account
     * @return getY() + getBorderTop()
     */
    public final int getInnerY() {
        return posY + borderTop;
    }

    /**
     * The inner width takes the left and right border into account.
     * @return the inner width - never negative
     */
    public final int getInnerWidth() {
        return Math.max(0, width - borderLeft - borderRight);
    }

    /**
     * The inner height takes the top and bottom border into account.
     * @return the inner height - never negative
     */
    public final int getInnerHeight() {
        return Math.max(0, height - borderTop - borderBottom);
    }

    /**
     * Returns the right X coordinate while taking the right border into account.
     * @return getInnerX() + getInnerWidth()
     */
    public final int getInnerRight() {
        return posX + Math.max(borderLeft, width - borderRight);
    }

    /**
     * Returns the bottom Y coordinate while taking the bottom border into account.
     * @return getInnerY() + getInnerHeight()
     */
    public final int getInnerBottom() {
        return posY + Math.max(borderTop, height - borderBottom);
    }

    /**
     * Checks if the given absolute (to this widget's tree) coordinates are inside this widget.
     *
     * @param x the X coordinate to test
     * @param y the Y coordinate to test
     * @return true if it was inside
     */
    public boolean isInside(int x, int y) {
        return (x >= posX) && (y >= posY) && (x < posX + width) && (y < posY + height);
    }

    /**
     * Sets a border for this widget.
     * @param top the top border
     * @param left the left border
     * @param bottom the bottom  border
     * @param right the right border
     * @return true if the border values have changed
     * @throws IllegalArgumentException if any of the parameters is negative.
     */
    public boolean setBorderSize(int top, int left, int bottom, int right) {
        if(top < 0 || left < 0 || bottom < 0 || right < 0) {
            throw new IllegalArgumentException("negative border size");
        }
        if(this.borderTop != top ||  this.borderBottom != bottom ||
                this.borderLeft != left || this.borderRight != right) {
            int innerWidth = getInnerWidth();
            int innerHeight = getInnerHeight();
            int deltaLeft = left - this.borderLeft;
            int deltaTop = top - this.borderTop;
            this.borderLeft = (short)left;
            this.borderTop = (short)top;
            this.borderRight = (short)right;
            this.borderBottom = (short)bottom;

            // first adjust child position
            if(childNodes != null && (deltaLeft != 0 || deltaTop != 0)) {
                for(int i=0,n=childNodes.size() ; i<n ; i++) {
                    adjustChildPosition(childNodes.get(i), deltaLeft, deltaTop);
                }
            }

            // now change size 因为宽度没变 但是border 变化了 所以内宽要发生变化
            setInnerSize(innerWidth, innerHeight);
            borderChanged();
            return true;
        }
        return false;
    }

    private static void adjustChildPosition(HtmlObject child, int deltaX, int deltaY) {
        child.setPositionImpl(child.posX + deltaX, child.posY + deltaY);
    }

    final boolean setPositionImpl(int x, int y) {
        int deltaX = x - posX;
        int deltaY = y - posY;
        if(deltaX != 0 || deltaY != 0) {
            this.posX = x;
            this.posY = y;

            if(childNodes != null) {
                for(int i=0,n=childNodes.size() ; i<n ; i++) {
                    adjustChildPosition(childNodes.get(i), deltaX, deltaY);
                }
            }

            positionChanged();

            if(propertyChangeSupport != null) {
                firePropertyChange("x", x - deltaX, x);
                firePropertyChange("y", y - deltaY, y);
            }
            return true;
        }
        return false;
    }
    /**
     * Report a bound property update to any registered listeners.
     *
     * @param propertyName The programmatic name of the property that was changed
     * @param oldValue The old value of the property
     * @param newValue The new value of the property
     * @see PropertyChangeSupport#firePropertyChange(java.lang.String, int, int)
     */
    protected final void firePropertyChange(String propertyName, int oldValue, int newValue) {
        if(propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    protected void positionChanged() {
    }

    private PropertyChangeSupport createPropertyChangeSupport() {
        if(propertyChangeSupport == null) {
            propertyChangeSupport = new PropertyChangeSupport(this);
        }
        return propertyChangeSupport;
    }
    /**
     * Add a PropertyChangeListener for all properties.
     *
     * @param listener The PropertyChangeListener to be added
     * @see PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        createPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    /**
     * Add a PropertyChangeListener for a specific property.
     *
     * @param propertyName The name of the property to listen on
     * @param listener The PropertyChangeListener to be added
     * @see PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        createPropertyChangeSupport().addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a PropertyChangeListener.
     *
     * @param listener The PropertyChangeListener to be removed
     * @see PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if(propertyChangeSupport != null) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    /**
     * Remove a PropertyChangeListener.
     *
     * @param propertyName The name of the property that was listened on
     * @param listener The PropertyChangeListener to be removed
     * @see PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        if(propertyChangeSupport != null) {
            propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
        }
    }

    /**
     * Changes the inner size of this widget.
     * Calls setSize after adding the border width/height.
     *
     * @param width The new width (exclusive border)
     * @param height The new height (exclusive border)
     * @return true if the size was changed, false if new size == old size
     * @see #setSize(int,int)
     */
    public boolean setInnerSize(int width, int height) {
        return setSize(width + borderLeft + borderRight, height + borderTop + borderBottom);
    }

    /**
     * Changes the size of this widget.
     * Zero size is allowed but not negative.
     * Size is not checked against parent widgets.
     *
     * When the size has changed then
     * - the parent widget's childChangedSize is called
     * - sizeChanged is called
     * - PropertyChangeEvent are fired for "width" and "height"
     *
     * This method should only be called from within the layout() method of the
     * parent. Otherwise it could lead to bad interaction with theming and result
     * in a wrong size after the theme has been applied.
     *
     * @param width The new width (including border)
     * @param height The new height (including border)
     * @return true if the size was changed, false if new size == old size
     * @throws java.lang.IllegalArgumentException if the size is negative
     * @see #sizeChanged()
     */
    public boolean setSize(int width, int height) {
        if(width < 0 || height < 0) {
            throw new IllegalArgumentException("negative size");
        }
        int oldWidth = this.width;
        int oldHeight = this.height;
        if(oldWidth != width || oldHeight != height) {
            this.width = width;
            this.height = height;

            sizeChanged();

            if(propertyChangeSupport != null) {
                firePropertyChange("width", oldWidth, width);
                firePropertyChange("height", oldHeight, height);
            }
            return true;
        }
        return false;
    }

    /**
     * Called when the size of this widget has changed.
     * The default implementation calls invalidateLayoutLocally. As size changes
     * are normally the result of the parent's layout() function.
     *
     * @see #invalidateLayoutLocally()
     */
    protected void sizeChanged() {
        invalidateLayoutLocally();
    }

    protected final void invalidateLayoutLocally() {
        if(layoutInvalid < LAYOUT_INVALID_LOCAL) {
            layoutInvalid = LAYOUT_INVALID_LOCAL;
            Document  document = getDocument();
            if(document != null) {
                document.hasInvalidLayouts = true;
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public short getBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(short borderLeft) {
        this.borderLeft = borderLeft;
    }

    public short getBorderTop() {
        return borderTop;
    }

    public void setBorderTop(short borderTop) {
        this.borderTop = borderTop;
    }

    public short getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(short borderRight) {
        this.borderRight = borderRight;
    }

    public short getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(short borderBottom) {
        this.borderBottom = borderBottom;
    }

    public short getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(short minHeight) {
        this.minHeight = minHeight;
    }

    public short getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(short minWidth) {
        this.minWidth = minWidth;
    }

    public short getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(short maxWidth) {
        this.maxWidth = maxWidth;
    }

    public short getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(short maxHeight) {
        this.maxHeight = maxHeight;
    }

    public short getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(short marginTop) {
        this.marginTop = marginTop;
    }

    public short getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(short marginLeft) {
        this.marginLeft = marginLeft;
    }

    public short getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(short marginBottom) {
        this.marginBottom = marginBottom;
    }

    public short getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(short marginRight) {
        this.marginRight = marginRight;
    }

    public short getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(short paddingTop) {
        this.paddingTop = paddingTop;
    }

    public short getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(short paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public short getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(short paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public short getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(short paddingRight) {
        this.paddingRight = paddingRight;
    }

    /**
     * Returns the GUI root of this widget tree if it has one.<p>
     *
     * Once a widget is added (indirectly) to a GUI object it will be part of
     * that GUI tree.<p>
     *
     * This method is thread safe.<p>
     *
     * Repeated calls may not return the same result. Use it like this:
     * <pre>
     * GUI gui = getGUI();
     * if(gui != null) {
     *     gui.invokeLater(....);
     * }
     * </pre>

     *
     * @return the GUI root or null if the root is not a GUI instance.
     */
    public final Document getDocument() {
        return document;
    }

    protected void borderChanged() {
        invalidateLayout();
    }

    public void invalidateLayout() {
        if(layoutInvalid < LAYOUT_INVALID_GLOBAL) {
            invalidateLayoutLocally();
            if(parentNode != null) {
                layoutInvalid = LAYOUT_INVALID_GLOBAL;
                parentNode.childInvalidateLayout(this);
            }
        }
    }

    protected void childInvalidateLayout(HtmlObject child) {
        invalidateLayout();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderTop= this.borderBottom=this.borderLeft=this.borderRight = (short)borderWidth;
    }
    private FocusGainedCause focusGainedCause;

    public HtmlObject clone(){
       HtmlObject o = null;
       try{
       o = (HtmlObject)super.clone();
       }catch(CloneNotSupportedException e){
       e.printStackTrace();
       }
       return o;
       }

   /* public boolean handleEvent(Event event){
        if(event.isMouseEvent()){
           // if(event.is)
            if(isInside(x,y)){

            }
        }

        for(HtmlObject htmlObject : childNodes){
            htmlObject.handleEvent(event);
        }
        return false;
    }
*/

    static boolean isMouseAction(Event evt) {
        Event.Type type = evt.getType();
        return type == Event.Type.MOUSE_BTNDOWN ||
                type == Event.Type.MOUSE_BTNUP ||
                type == Event.Type.MOUSE_CLICKED ||
                type == Event.Type.MOUSE_DRAGGED;
    }
    protected boolean canAcceptKeyboardFocus;
    public boolean canAcceptKeyboardFocus() {
        return canAcceptKeyboardFocus;
    }
    protected boolean isMouseInside(Event evt) {
        return isInside(evt.getMouseX(), evt.getMouseY());
    }
    protected HtmlObject lastChildMouseOver;
    protected HtmlObject focusChild;
    boolean setMouseOverChild(HtmlObject child, Event evt) {//这个方法用来设置鼠标经过的主键 什么时候返回true呢 鼠标任然在某个元素上 或者 之前是没有聚焦的 现在有了并且有子元素消费了事件 那么说明有元素响应了 鼠标移入事件
//        LogUtil.println(this.id+":setMouseOverChild");
        if (lastChildMouseOver != child) {//如果当前节点的上一个鼠标经过的是空 且询问的节点是非空 或者
            if(child != null) {
                HtmlObject result = child.routeMouseEvent(evt.createSubEvent(Event.Type.MOUSE_ENTERED));
                if(result == null) {//child的子元素没有处理掉 child也没有handle掉
                    // this child widget doesn't want mouse events
                    return false;
                }
            }
            if (lastChildMouseOver != null) {
                lastChildMouseOver.routeMouseEvent(evt.createSubEvent(Event.Type.MOUSE_EXITED));
            }
            lastChildMouseOver = child;
        }
        return true;
    }
    HtmlObject routeMouseEvent(Event evt) {//这个方法很重要是判断是否鼠标在某个组件的地方
//        LogUtil.println("routeMouseEvent"+this.id);
        assert !evt.isMouseDragEvent();
        //evt = translateMouseEvent(evt);//x y 进行调整== 转换成相对位置
        if(childNodes != null) {
            for(int i=childNodes.size(); i-->0 ;) {//对每个子元素进行判定
                HtmlObject child = childNodes.get(i);
                if(child.visible && child.isMouseInside(evt)) {
//                    LogUtil.println(this.id+"x:"+evt.mouseX+"y:"+evt.mouseY +"在"+child.id+"["+child.posX+","+child.posY+"]里 type"+evt.getType());
                    // we send the real event only only if we can transfer the mouse "focus" to this child
                    if(setMouseOverChild(child, evt)) {// 向子元素摊派 进入离开事件 说明事件被handle 并且返回了 true
                        //LogUtil.println("setMouseOverChild 返回了true"+this.id+"x:"+evt.mouseX+"y:"+evt.mouseY +"在"+child.id+"["+child.posX+","+child.posY+"]里 type"+evt.getType());
                        if(evt.getType() == Event.Type.MOUSE_ENTERED ||
                                evt.getType() == Event.Type.MOUSE_EXITED) {
                            return child;
                        }
                        HtmlObject result = child.routeMouseEvent(evt);//如果不是鼠标进入或者离开时间 比如moved事件 向子元素摊派事件本身 如果本身夜奔
                        if(result != null) {
                           // LogUtil.println("result!=null"+this.id+"x:"+evt.mouseX+"y:"+evt.mouseY +"在"+child.id+"["+child.posX+","+child.posY+"]里 type"+evt.getType());
                            // need to check if the focus was transfered to this child or its descendants
                            // if not we need to transfer focus on mouse click here
                            // this can happen if we click on a widget which doesn't want the keyboard focus itself
                            if(evt.getType() == Event.Type.MOUSE_BTNDOWN && focusChild != child) {
                                try {
                                    child.focusGainedCause = FocusGainedCause.MOUSE_BTNDOWN;
                                    if(child.isEnabled() && child.canAcceptKeyboardFocus()) {
                                        requestKeyboardFocus(child);
                                    }
                                } finally {
                                    child.focusGainedCause = null;
                                }
                            }
                            return result;
                        }
                        // widget no longer wants mouse events
                    }
                    // found a widget - but it doesn't want mouse events
                    // so assumes it's "invisible" for the mouse
                }
            }
        }

        // the following code is only executed for the widget which received
        // the click event. That's why we can call {@code requestKeyboardFocus(null)}
        if(evt.getType() == Event.Type.MOUSE_BTNDOWN && isEnabled() && canAcceptKeyboardFocus()) {
            try {
                focusGainedCause = FocusGainedCause.MOUSE_BTNDOWN;
                if(focusChild == null) {
                    requestKeyboardFocus();
                } else {
                    requestKeyboardFocus(null);
                }
            } finally {
                focusGainedCause = null;
            }
        }
        if(evt.getType() != Event.Type.MOUSE_WHEEL) {
            // no child has mouse over
            setMouseOverChild(null, evt);
        }
        if(!isEnabled() && isMouseAction(evt)) {
            return this;
        }
        if(handleEvent(evt)) {
            return this;
        }
        return null;
    }
    private InputMap inputMap;
    protected List<HtmlObject> getKeyboardFocusOrder() {
        if(childNodes == null) {
            return Collections.<HtmlObject>emptyList();
        }
        return Collections.unmodifiableList(childNodes);
    }
    private boolean depthFocusTraversal = true;

    private int collectFocusOrderList(ArrayList<HtmlObject> list) {
        int idx = -1;
        for(HtmlObject child : getKeyboardFocusOrder()) {
            if(child.visible && child.isEnabled()) {
                if(child.canAcceptKeyboardFocus) {
                    if(child == focusChild) {
                        idx = list.size();
                    }
                    list.add(child);
                }
                if(child.depthFocusTraversal) {
                    int subIdx = child.collectFocusOrderList(list);
                    if(subIdx != -1) {
                        idx = subIdx;
                    }
                }
            }
        }
        return idx;
    }

    private boolean moveFocus(boolean relative, int dir) {
        ArrayList<HtmlObject> focusList = new ArrayList<HtmlObject>();
        int curIndex = collectFocusOrderList(focusList);
        if(focusList.isEmpty()) {
            return false;
        }
        if(dir < 0) {
            if(!relative || --curIndex < 0) {
                curIndex = focusList.size() - 1;
            }
        } else if(!relative || ++curIndex >= focusList.size()) {
            curIndex = 0;
        }
        HtmlObject widget = focusList.get(curIndex);
        try {
            widget.focusGainedCause = FocusGainedCause.FOCUS_KEY;
            widget.requestKeyboardFocus(null);
            widget.requestKeyboardFocus();
        } finally {
            widget.focusGainedCause = null;
        }
        return true;
    }
    public boolean focusNextChild() {
        return moveFocus(true, +1);
    }

    public boolean focusPrevChild() {
        return moveFocus(true, -1);
    }

    public boolean focusFirstChild() {
        return moveFocus(false, +1);
    }
    public boolean hasKeyboardFocus() {
        if(getParentNode() != null) {
            return getParentNode().focusChild == this;
        }
        return false;
    }
    private boolean hasOpenPopup;
    public boolean hasOpenPopups() {
        return hasOpenPopup;
    }
    public boolean focusLastChild() {
        return moveFocus(false, -1);
    }
    void handleFocusKeyEvent(Event evt) {
        if(evt.isKeyPressedEvent()) {
            if((evt.getModifiers() & Event.MODIFIER_SHIFT) != 0) {
                focusPrevChild();
            } else {
                focusNextChild();
            }
        }
    }

    private boolean focusKeyEnabled = true;
    private boolean handleKeyEvent(Event evt) {
        if(childNodes != null) {
            if(focusKeyEnabled && guiInstance != null) {
                guiInstance.setFocusKeyWidget(this);
            }
            if(focusChild != null && focusChild.isVisible()) {
                if(focusChild.handleEvent(evt)) {
                    return true;
                }
            }
        }
        if(inputMap != null) {
            String action = inputMap.mapEvent(evt);
            if(action != null) {
                if(handleKeyStrokeAction(action, evt)) {
                    return true;
                }
                /*if(WARN_ON_UNHANDLED_ACTION) {
                    java.util.logging.Logger.getLogger(getClass().getName()).log(Level.WARNING,
                            "Unhandled action ''{0}'' for class ''{1}''",
                            new Object[]{ action, getClass().getName() });
                }*/
            }
        }
        return false;
    }
    private ActionMap actionMap;
    protected boolean handleKeyStrokeAction(String action, Event event) {
        if(actionMap != null) {
            return actionMap.invoke(action, event);
        }
        return false;
    }
    protected boolean handleEvent(Event evt) {
        if(evt.isKeyEvent()) {
            return handleKeyEvent(evt);
        }
        return false;
    }
    public boolean isEnabled(){
        return !disabled;
    }
    /**
     * Called when this widget has lost the keyboard focus.
     * The default implementation does nothing.
     */
    protected void keyboardFocusLost() {
    }
    private void recursivelyChildFocusLost(HtmlObject w) {
        while(w != null) {
            HtmlObject next = w.focusChild;//递归子元素失去焦点
            //can't resovle
           /* if(!w.sharedAnimState) {//sharedAnimState=false
                w.animState.setAnimationState(STATE_KEYBOARD_FOCUS, false);//触发失去焦点事件
            }*/
            try {
                w.keyboardFocusLost();//设置失去焦点
            } catch(Exception ex) {
                ex.printStackTrace();
                //getLogger().log(Level.SEVERE, "Exception in keyboardFocusLost()", ex);
            }
            w.focusChild = null;
            w = next;
        }
    }
    protected boolean requestKeyboardFocus(HtmlObject child) {
        if(child != null && child.parentNode != this) {
            throw new IllegalArgumentException("not a direct child");
        }
        if(focusChild != child) {//如果当前的focusChild 不是指定的child  focusCHild是之前的editfield 现在的child是scrollpane 因为我们点击了scrollpane
            if(child == null) {
                recursivelyChildFocusLost(focusChild);//让focusCHild的子节点都变成非focus 状态
                focusChild = null;//
                keyboardFocusChildChanged(null);//调用子元素的失去焦点事件
            } else {
                boolean clear = focusTransferStart();//false
                try {
                    // first request focus for ourself
                    {
                        FocusGainedCause savedCause = focusGainedCause;//null
                        if(savedCause == null) {//当前是dialoglayout  children有两个 scrollpane 和editfield
                            focusGainedCause = FocusGainedCause.CHILD_FOCUSED;
                        }
                        try {
                            if(!requestKeyboardFocus()) {
                                return false;
                            }
                        } finally {
                            focusGainedCause = savedCause;//null
                        }
                    }

                    // second change focused child
                    recursivelyChildFocusLost(focusChild);//之前的焦点需要释放 原来的焦点链条需要变动
                    focusChild = child;
                    keyboardFocusChildChanged(child);//现在的焦点链条要连上 触发焦点事件
                    /*if(!child.sharedAnimState) {//如果之前动画状态没有
                        child.animState.setAnimationState(STATE_KEYBOARD_FOCUS, true);//现在的动画状态是获取焦点状态
                    }*/

                    // last inform the child widget why it gained keyboard focus
                    FocusGainedCause cause = child.focusGainedCause;//获取焦点的原因是鼠标点击了 各种原因参见FocusGainedCause
                    HtmlObject[] fti = focusTransferInfo.get();
                    child.keyboardFocusGained(//获取之前的焦点
                            (cause != null) ? cause : FocusGainedCause.MANUAL,
                            (fti != null) ? fti[0] : null);
                } finally {
                    focusTransferClear(clear);//清除之前的焦点 在threadlocal中的记录
                }
            }
        }
        /*if(!sharedAnimState) {
            animState.setAnimationState(STATE_HAS_FOCUSED_CHILD, focusChild != null);
        }*/
        return focusChild != null;
    }

    protected void keyboardFocusGained(FocusGainedCause cause, HtmlObject previousWidget) {
        // System.out.println(this + " " + cause + " " + previousWidget);
        keyboardFocusGained();
    }
    /**
     * Called when this widget has gained the keyboard focus.
     * The default implementation does nothing.
     *
     * @see //#keyboardFocusGained(de.matthiasmann.twl.FocusGainedCause, de.matthiasmann.twl.Widget)
     */
    protected void keyboardFocusGained() {
    }
    private void focusTransferClear(boolean clear) {
        if(clear) {
            focusTransferInfo.set(null);
        }
    }
    /**
     * The current keyboard focus child has changed.
     * The default implementation does nothing.
     *
     * @param child The child which has now the keyboard focus in this hierachy level or null
     */
    protected void keyboardFocusChildChanged(HtmlObject child) {
    }
    private static final ThreadLocal<HtmlObject[]> focusTransferInfo = new ThreadLocal<HtmlObject[]>();
    protected volatile Document guiInstance;
    private boolean focusTransferStart() {
        HtmlObject[] fti = focusTransferInfo.get();//存储了焦点传递状态信息 有之前的焦点元素 和现在的焦点元素
        if(fti == null) {//fti必须是null否则 什么都免谈
            HtmlObject root = getRootWidget();
            HtmlObject w = root;
            while(w.focusChild != null) {
                w = w.focusChild;
            }
            if(w == root) {
                w = null;
            }
            focusTransferInfo.set(new HtmlObject[]{ w });//存储了最终的focus 最底层的元素 存储的是editfield
            return true;
        }
        return false;
    }

    public final HtmlObject getRootWidget() {
        HtmlObject w = this;
        HtmlObject p;
        while((p=w.parentNode) != null) {
            w = p;
        }
        return w;
    }

    //private HtmlObject parent;
    public void setParent(HtmlObject object){
        this.parentNode = object;
    }

    public void insertChild(HtmlObject child, int index) throws IndexOutOfBoundsException {
        if(child == null) {
            throw new IllegalArgumentException("child is null");
        }
        if(child == this) {
            throw new IllegalArgumentException("can't add to self");
        }
        if(child.parentNode != null) {
            throw new IllegalArgumentException("child widget already in tree");
        }
        if(childNodes == null) {
            childNodes = new ArrayList<HtmlObject>();
        }
        if(index < 0 || index > childNodes.size()) {
            throw new IndexOutOfBoundsException();
        }
        child.setParent(this);  // can throw exception - see PopupWindow
        childNodes.add(index, child);
        Document gui = this.getDocument();
        if(gui != null) {
            child.recursivelySetGUI(gui);
        }
        adjustChildPosition(child, posX + borderLeft, posY + borderTop);
        child.recursivelyEnabledChanged(null, !this.disabled);
        if(gui != null) {
            child.recursivelyAddToGUI(gui);
        }
        /*if(themeManager != null) {
            child.applyTheme(themeManager);
        }*/
        try {
            childAdded(child);
        } catch(Exception ex) {
          //  getLogger().log(Level.SEVERE, "Exception in childAdded()", ex);
        }
        // A newly added child can't have open popups
        // because it needs a GUI for this - and it had no parent up to now
    }
    protected void childAdded(HtmlObject child) {
        invalidateLayout();
    }


    public void recursivelySetGUI(Document  doc) {
        if(this.getParentNode()!=null){
            this.index= getParentNode().index-0.01f;
        }

        assert guiInstance == null : "guiInstance must be null";
        guiInstance = doc;
        if(childNodes != null) {
            for(int i=childNodes.size() ; i-->0 ;) {
                childNodes.get(i).recursivelySetGUI(doc);
            }
        }
    }
    private boolean locallyEnabled = true;
    public boolean getEnabled(){
        return !disabled;
    }
    private void recursivelyEnabledChanged(GUI gui, boolean enabled) {
        /*
        enabled &= locallyEnabled;
        if(this.enabled != enabled) {
            this.enabled = enabled;
            if(!sharedAnimState) {
                getAnimationState().setAnimationState(STATE_DISABLED, !enabled);
            }
            if(!enabled) {
                if(gui != null) {
                    gui.widgetDisabled(this);
                }
                try {
                    widgetDisabled();
                } catch(Exception ex) {
                    getLogger().log(Level.SEVERE, "Exception in widgetDisabled()", ex);
                }
                try {
                    giveupKeyboardFocus();
                } catch(Exception ex) {
                    getLogger().log(Level.SEVERE, "Exception in giveupKeyboardFocus()", ex);
                }
            }
            try {
                firePropertyChange("enabled", !enabled, enabled);
            } catch(Exception ex) {
                getLogger().log(Level.SEVERE, "Exception in firePropertyChange(\"enabled\")", ex);
            }
            if(children != null) {
                for(int i=children.size() ; i-->0 ;) {
                    Widget child = children.get(i);
                    child.recursivelyEnabledChanged(gui, enabled);
                }
            }
        }*/
    }

    private void recursivelyAddToGUI(HtmlObject gui) {
        /*assert guiInstance == gui : "guiInstance must be equal to gui";
        if(layoutInvalid != 0) {
            gui.hasInvalidLayouts = true;
        }
        if(!sharedAnimState) {
            animState.setGUI(gui);
        }
        try {
            afterAddToGUI(gui);
        } catch(Exception ex) {
            getLogger().log(Level.SEVERE, "Exception in afterAddToGUI()", ex);
        }
        if(children != null) {
            for(int i=children.size() ; i-->0 ;) {
                children.get(i).recursivelyAddToGUI(gui);
            }
        }*/
    }

    public boolean requestKeyboardFocus() {
        if(parentNode != null && visible) {//如果父节点不为空 并且自己是课件的
            if(parentNode.focusChild == this) {//如果父节点的焦点是自己
                return true;
            }

            boolean clear = focusTransferStart();
            try {
                return parentNode.requestKeyboardFocus(this);
            } finally {
                focusTransferClear(clear);//scrollpane的当前线程变量 里的当前焦点清空
            }
        }
        return false;
    }

    protected void addActionMapping(String action, String methodName, Object ... params) {
        getOrCreateActionMap().addMapping(action, this, methodName, params, ActionMap.FLAG_ON_PRESSED);
    }

    public ActionMap getOrCreateActionMap() {
        if(actionMap == null) {
            actionMap = new ActionMap();
        }
        return actionMap;
    }

    /**
     * Returns the current input map.
     * @return the current input map or null.
     */
    public InputMap getInputMap() {
        return inputMap;
    }

    /**
     * Sets the input map for key strokes.
     *
     * @param inputMap the input map or null.
     * @see #handleKeyStrokeAction(java.lang.String, de.matthiasmann.twl.Event)
     */
    public void setInputMap(InputMap inputMap) {
        this.inputMap = inputMap;
    }

    /**
     * Clean up GL resources. When overwritten then super method must be called.
     */


    public AnimationState getAnimationState() {
        return animState;
    }
    public HtmlObject(){
        this.sharedAnimState=false;
        this.animState = new AnimationState();

    }

    private final AnimationState animState;
    private final boolean sharedAnimState;


    public HtmlObject(AnimationState animState, boolean inherit) {
        // determine the default theme name from the class name of this instance
        // eg class Label => "label"
        Class<?> clazz = getClass();
       /* do {
            //theme = clazz.getSimpleName().toLowerCase(Locale.ENGLISH);
            clazz = clazz.getSuperclass();
        } while (theme.length() == 0 && clazz != null);*/

        if(animState == null || inherit) {
            this.animState = new AnimationState(animState);
            this.sharedAnimState = false;
        } else {
            this.animState = animState;
            this.sharedAnimState = true;
        }
    }
    protected void paintWidget(Document doc) {
    }
    final void drawWidget(Document gui) {//drawWidget -> paint
        /*if(renderOffscreen != null) {
            drawWidgetOffscreen(gui);
            return;
        }
        if(tintAnimator != null && tintAnimator.hasTint()) {
            drawWidgetTint(gui);
            return;
        }
        if(clip) {
            drawWidgetClip(gui);
            return;
        }*/
        paint(gui);
    }
    protected void paintChildren(Document gui) {
        if(childNodes != null) {
            for(int i=0,n=childNodes.size() ; i<n ; i++) {
                HtmlObject child = childNodes.get(i);
                if(child.visible) {
                    child.drawWidget(gui);
                }
            }
        }
    }
    public int getPreferredInnerHeight() {
        int bottom = getInnerY();
        if(childNodes != null) {
            for(int i=0,n=childNodes.size() ; i<n ; i++) {
                HtmlObject child = childNodes.get(i);
                bottom = Math.max(bottom, child.getBottom());
            }
        }
        return bottom - getInnerY();
    }
    public de.matthiasmann.twl.renderer.Image getOverlay() {
        return overlay;
    }
    private de.matthiasmann.twl.renderer.Image overlay;
    protected void paint(Document doc) {
        paintBackground(doc);
        paintWidget(doc);
        paintChildren(doc);
        paintOverlay(doc);
    }

    protected void paintOverlay(Document gui) {
        de.matthiasmann.twl.renderer.Image ovImage = getOverlay();
        if(ovImage != null) {
            ovImage.draw(getAnimationState(), posX, posY, width, height);
        }
    }
    protected void paintBackground(Document gui) {
        de.matthiasmann.twl.renderer.Image bgImage = getBackground();
        if(bgImage != null) {
            bgImage.draw(getAnimationState(), posX, posY, width, height);
        }
    }
    private de.matthiasmann.twl.renderer.Image background;
    public de.matthiasmann.twl.renderer.Image getBackground() {
        return background;
    }
    /**
     * Clean up GL resources. When overwritten then super method must be called.
     */
    public void destroy() {
        if(childNodes != null) {
            for(int i=0,n=childNodes.size() ; i<n ; i++) {
                childNodes.get(i).destroy();
            }
        }
        /*if(offscreenSurface != null) {
            offscreenSurface.destroy();
            offscreenSurface = null;
        }*/
    }

    public int getPreferredInnerWidth() {//会用子元素里最靠右边的right属性减去innerx
        int right = getInnerX();
        if(childNodes != null) {
            for(int i=0,n=childNodes.size() ; i<n ; i++) {
                HtmlObject child = childNodes.get(i);
                right = Math.max(right, child.getRight());
            }
        }
        return right - getInnerX();
    }

}


