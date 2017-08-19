package cola.machine.game.myblocks.model;

import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;

public class LightBlock extends BaseBlock{
  int front;
    int left;
    int right;
    int back;
    int top;
    int bottom;

  @Override
  public void update(float x, float y, float z, float width, float height, float thick) {

  }

  @Override
  public void update() {

  }

  @Override
  public BaseBlock copy() {
    return null;
  }

    @Override
    public void render(ShaderConfig config, Vao vao, int x, int y, int z, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }
}
