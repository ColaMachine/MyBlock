package cola.machine.game.myblocks.model.textture;

/**
 * Created by luying on 16/9/12.
 */
public class Shape {
String name ;
    Shape front;
    Shape back;
    Shape left;
    Shape right;
    Shape top;
int width;
    int height;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shape getFront() {
        return front;
    }

    public void setFront(Shape front) {
        this.front = front;
    }

    public Shape getBack() {
        return back;
    }

    public void setBack(Shape back) {
        this.back = back;
    }

    public Shape getLeft() {
        return left;
    }

    public void setLeft(Shape left) {
        this.left = left;
    }

    public Shape getRight() {
        return right;
    }

    public void setRight(Shape right) {
        this.right = right;
    }

    public Shape getTop() {
        return top;
    }

    public void setTop(Shape top) {
        this.top = top;
    }

    public Shape getBottom() {
        return bottom;
    }

    public void setBottom(Shape bottom) {
        this.bottom = bottom;
    }

    Shape bottom;

}
