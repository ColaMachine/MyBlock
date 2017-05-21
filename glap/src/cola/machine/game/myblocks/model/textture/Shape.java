package cola.machine.game.myblocks.model.textture;

import com.dozenx.game.engine.element.model.ShapeFace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luying on 16/9/12.
 */
public class Shape {
    public int getShapeType() {
        return shapeType;
    }

    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }
    ShapeFace frontFace;
    ShapeFace backFace;
    ShapeFace topFace;

    public ShapeFace getFrontFace() {
        return frontFace;
    }

    public void setFrontFace(ShapeFace frontFace) {
        this.frontFace = frontFace;
    }

    public ShapeFace getBackFace() {
        return backFace;
    }

    public void setBackFace(ShapeFace backFace) {
        this.backFace = backFace;
    }

    public ShapeFace getTopFace() {
        return topFace;
    }

    public void setTopFace(ShapeFace topFace) {
        this.topFace = topFace;
    }

    public ShapeFace getBottomFace() {
        return bottomFace;
    }

    public void setBottomFace(ShapeFace bottomFace) {
        this.bottomFace = bottomFace;
    }

    public ShapeFace getLeftFace() {
        return leftFace;
    }

    public void setLeftFace(ShapeFace leftFace) {
        this.leftFace = leftFace;
    }

    public ShapeFace getRightFace() {
        return rightFace;
    }

    public void setRightFace(ShapeFace rightFace) {
        this.rightFace = rightFace;
    }

    ShapeFace bottomFace;
    ShapeFace leftFace;
    ShapeFace rightFace;
    String name ;
    int shapeType;
    TextureInfo front;
    TextureInfo back;
    TextureInfo left;
    TextureInfo right;
    TextureInfo top;
    TextureInfo bottom;
    float width;
    float height;
    float thick;
    float p_posi_x;

    public float getP_posi_x() {
        return p_posi_x;
    }

    public void setP_posi_x(float p_posi_x) {
        this.p_posi_x = p_posi_x;
    }

    public float getP_posi_y() {
        return p_posi_y;
    }

    public void setP_posi_y(float p_posi_y) {
        this.p_posi_y = p_posi_y;
    }

    public float getP_posi_z() {
        return p_posi_z;
    }

    public void setP_posi_z(float p_posi_z) {
        this.p_posi_z = p_posi_z;
    }

    public float getC_posi_x() {
        return c_posi_x;
    }

    public void setC_posi_x(float c_posi_x) {
        this.c_posi_x = c_posi_x;
    }

    public float getC_posi_y() {
        return c_posi_y;
    }

    public void setC_posi_y(float c_posi_y) {
        this.c_posi_y = c_posi_y;
    }

    public float getC_posi_z() {
        return c_posi_z;
    }

    public void setC_posi_z(float c_posi_z) {
        this.c_posi_z = c_posi_z;
    }

    float p_posi_y;
    float p_posi_z;
    float c_posi_x;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getThick() {
        return thick;
    }

    public void setThick(float thick) {
        this.thick = thick;
    }

    float c_posi_y;
    float c_posi_z;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextureInfo getFront() {
        return front;
    }

    public void setFront(TextureInfo front) {
        this.front = front;
    }

    public TextureInfo getBack() {
        return back;
    }

    public void setBack(TextureInfo back) {
        this.back = back;
    }

    public TextureInfo getLeft() {
        return left;
    }

    public void setLeft(TextureInfo left) {
        this.left = left;
    }

    public TextureInfo getRight() {
        return right;
    }

    public void setRight( TextureInfo right) {
        this.right = right;
    }

    public TextureInfo getTop() {
        return top;
    }

    public void setTop(TextureInfo top) {
        this.top = top;
    }

    public TextureInfo getBottom() {
        return bottom;
    }

    public void setBottom(TextureInfo bottom) {
        this.bottom = bottom;
    }


    /**
     * 根据父节点的属性来求子属性的值
     * @param str
     * @param width
     * @param height
     * @param thick
     * @param pwidth
     * @param pheight
     * @param pthick
     * @return
     * @throws Exception
     */
    public static Float parsePosition(String str,Float width,Float height,Float thick,Float pwidth,Float pheight,Float pthick) throws Exception {

    Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
    Matcher matcher = pattern.matcher(str);
    if(matcher.matches()){
        return Float.valueOf(str);
    }


     pattern = Pattern.compile("(([a-zA-Z_]+)\\.)?([a-zA-Z_]+)(\\*(\\d+))?/?(\\d+)?");
     matcher = pattern.matcher(str);


    if(matcher.matches()){
        String parentname = matcher.group(2);
        String attr = matcher.group(3);
        String fenzi = matcher.group(5);
        String fenmu= matcher.group(6);
        String parent = matcher.group(1);
        Float value =0f;
        Float pvalue=0f;
        if(attr.equals("width")){
            value=width;  pvalue=pwidth;
        }else if(attr.equals("height")){
            value=height;  pvalue=pheight;
        }else if(attr.equals("thick")){
            value=thick;  pvalue=pthick;
        }else{
            throw new Exception("解析shape position时候出错: attr不能为空" );
        }

        if(parentname!=null) {
             value=pvalue;
        }

        if(fenzi!=null){
            value*=Float.valueOf(fenzi);
        }
        if(fenmu!=null){
            value/=Float.valueOf(fenmu);
        }


        return value;


    }else{
        throw new Exception("解析shape position时候出错 无法解析"+str );
    }
}


    public static void main(String args[]){

        Pattern pattern = Pattern.compile("(([a-zA-Z_]+)\\.)?([a-zA-Z_]+)(\\*(\\d+))?/?(\\d+)?");
        Matcher matcher = pattern.matcher("parent.height");

        if(matcher.matches()){
            System.out.println(matcher.groupCount());
            int count = matcher.groupCount();
            for(int i=1;i<=count;i++){
                System.out.println(i+":"+matcher.group(i));
               // System.out.println(i+":"+(matcher.group(i)==null));
            }
        }
    }
}
