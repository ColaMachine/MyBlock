/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年9月6日 下午6:50:12
* 创建作者：张智威
* 文件名称：MapNode.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.dozenx.game.network.server.service;

public class MapNode {
public int x,y,z;
public boolean reachable;
public int sur,value;//sur的每个bit 都标志了东南西北 等8个方位
}
