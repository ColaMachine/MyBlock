package cola.machine.game.myblocks.model.ui.html;

import cola.machine.game.myblocks.engine.entitySystem.event.Event;
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
import de.matthiasmann.twl.GUI;
import glapp.GLApp;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 14-9-17.
 */
public class HtmlObject {

    private static final int LAYOUT_INVALID_LOCAL  = 1;
    private static final int LAYOUT_INVALID_GLOBAL = 3;

    private static final Logger logger = LoggerFactory.getLogger(HtmlObject.class);
    public MouseEventReceiver mouseEventReceiver;
    public KeyEventReceiver keyEventReceiver;
    public  HtmlObject parentNode;
    public   List<HtmlObject> childNodes =new ArrayList<HtmlObject>();
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

    private  float fontSize;

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
    private  int width;
    /** the outer height**/
    private  int height;
    /**disabled**/
    private boolean disabled = false;
    volatile Document document;
    private PropertyChangeSupport propertyChangeSupport;

    private short borderLeft;
    private short borderTop;
    private short borderRight;
    private short borderBottom;

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
        if(parentNode!=null){
            parentNode.removeChild(htmlObject);
        }
        childNodes.add(htmlObject);htmlObject.parentNode=this;
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
            width=parentNode.getWidth();///this.parentNode.childNodes.size() because of tr's width  may be the whole with of table
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
    public float getOffsetLeft(){
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
        if(this.parentNode!=null)
            return this.parentNode.getOffsetLeft()+this.left;
        else{
            return this.left;
        }
    }

    public int getTop(){

            return this.top;

    }

    public int getOffsetTop(){
        if(this.parentNode!=null)
            return this.parentNode.getOffsetTop()+this.top;
        else{
            return this.top;
        }
    }

    public int getOffsetBottom(){
        if(this.parentNode!=null)
            return this.getOffsetTop()+this.getHeight();
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

    public void update(){
    	
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
        this.posX=this.getLeft();
        this.posY=this.getTop();
        //this.maxX=this.minX+this.getWidth();
        //this.maxY=this.minY+this.getHeight();
        //ShaderUtils.create2dimageVao(vao,minX,minY,this.getWidth(),this.getHeight());

        //ShaderUtils.create2dimageVao(vao,minX,minY,maxX-minX,maxY-minY);
        //ShaderUtils.createBorderVao(borderVao,minX,minY,maxX-minX,maxY-minY);

        if(this.getWidth()==0){
            logger.error("maxX can't not be 0");
            System.exit(0);
        }
        for(int i=0;i<this.childNodes.size();i++){
        	//System.out.println("2div id:"+id);
            this.childNodes.get(i).update();
        }
    }
    Vao vao =new Vao();
    Vao borderVao =new Vao();
    public void  shaderRender(){
        if(!visible)return;
        if(this.getBackgroundColor()!=null){
            ShaderUtils.draw2dColor(this.getBackgroundColor(),getInnerX(),getInnerY(),getInnerWidth(),getInnerHeight());   OpenglUtils.checkGLError();
            //ShaderUtils.draw2DColorWithShader(this.getBackgroundColor(),ShaderUtils.get2DColorShaderConfig(),vao);
        }
        if(this.getBackgroundImage()!=null){
            /*if(this.background_image.equals("toolbar")){
        		System.out.println("toolbar render");
        	}*/
            ShaderUtils.draw2dImg(this.getBackgroundImage(), getInnerX(),getInnerY(),getInnerWidth(),getInnerHeight());   OpenglUtils.checkGLError();
            //ShaderUtils.draw2DImageWithShader(ShaderUtils.get2DImageShaderConfig(), vao);
            //OpenglUtils.draw2DImageShader(i);
            //OpenglUtils.draw2DImageShader(textureInfo.textureHandle,1,1,50,50);
        }

        if(this.innerText!=null && innerText.length()>0){
            //GL11.glColor4f(1, 1, 1, 1);
            ShaderUtils.printText("你好",getInnerX(),getInnerY(),this.getFontSize());   OpenglUtils.checkGLError();
            //GLApp.print((int)minX,(int)minY,this.innerText);
        }
        if(this.borderLeft>0){
            ShaderUtils.draw2dColor(this.getBorderColor(),posX,posY,borderLeft,height);   OpenglUtils.checkGLError();
        }
        if(this.borderTop>0){
            ShaderUtils.draw2dColor(this.getBorderColor(),posX,posY,width,borderTop);   OpenglUtils.checkGLError();
        }
        if(this.borderRight>0){
            ShaderUtils.draw2dColor(this.getBorderColor(),posX+width-borderRight,posY,borderRight,height);   OpenglUtils.checkGLError();
        }
        if(this.borderBottom>0){
            ShaderUtils.draw2dColor(this.getBorderColor(),posX,posY+height-borderBottom,width,borderBottom);   OpenglUtils.checkGLError();
        }

        for(HtmlObject htmlObject:this.childNodes){
            //if(Switcher.SHADER_ENABLE){
                htmlObject.shaderRender();
            //}else{
            //    htmlObject.render();
            //}

        }

      /*  for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).render();
        }*/


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
    public void onClick(Event event){
        if(!visible)
            return;
        if(event.cancelBubble)
            return;

    	if(this.isInside(event.x, event.y)){
            if(this.mouseEventReceiver!=null) {//System.out.println("the clicked element id:"+this.id);
                this.mouseEventReceiver.mouseClick(event.x, event.y, this);
                event.cancelBubble=true;
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
}
