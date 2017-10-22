package cola.machine.game.myblocks.physic;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/5/29.
 */
public class BulletResultDTO {
    public GL_Vector targetPoint;
    public GL_Vector placePoint;
    public BaseBlock targetBlock;
    public int targetChunX;
    public int targetChunZ;
}
