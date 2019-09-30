package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.BoneRotateImageBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.physic.BulletResultDTO;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.game.engine.Role.model.PlayerModel;
import com.dozenx.game.engine.command.ItemMainType;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.server.bean.PlayerStatus;
import glmodel.GL_Vector;
import org.lwjgl.Sys;

public class PlayerCharacter extends MonoBehaviour {

    public float speed;
    public float turnSpeed;
  //  public ParticleSystem explosionParticles;//爆炸效果
   // public Rigidbody shell;
//    public Transform muzzle;
//    public Transform transform;
//
//    public void update(){
//        transform.translate()
//    }
}
