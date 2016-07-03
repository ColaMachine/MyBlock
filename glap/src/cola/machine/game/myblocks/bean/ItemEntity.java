package cola.machine.game.myblocks.bean;

/**
 * Created by luying on 16/7/3.
 */
public class ItemEntity {
    int id;
    String name;
    public ItemEntity(String name, int num)
    {
        this.name=name;
        this.num=num;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
