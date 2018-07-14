package com.dozenx.game.engine.item.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:34 2018/7/9
 * @Modified By:
 */
public class CodeLine {
    boolean closed=false;
    public void end(String lastWord){

    }

    public void push(char c){

    }

    StringBuffer sb = new StringBuffer();
    CodeLine exPression = null;
    String lastWord;
    boolean stringStart=false;
    List<CodeLine> exPressions = new ArrayList<>();
}
