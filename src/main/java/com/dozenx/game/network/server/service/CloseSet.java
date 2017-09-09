/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年9月6日 下午6:52:18
* 创建作者：张智威
* 文件名称：CloseSet.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.dozenx.game.network.server.service;

public class CloseSet {
    public MapNode cur;//mapnode 相当于一个格子 记载了坐标 位置 周围四格是否有物体 自身的value
public boolean vis;//是否走过
public CloseSet from;//from 决定了箭头的指向开始的地方
public float F,G;//g是
public int H;//所有的点到目的地的距离 cls[i][j].H = Math.abs(dx - i) + Math.abs(dy - j);   
}
