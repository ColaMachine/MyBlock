package com.dozenx.game.engine.item.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:34 2018/7/9
 * @Modified By:
 */
public class AssignCodeLine extends CodeLine {
    public AssignCodeLine(String name) {
        this.leftVariable = name;
    }

    String leftVariable;
    CodeLine rightCodeLine;

    public void push(char c) {
        if (exPression != null) {

            exPression.push(c);
            if (exPression.closed) {
                rightCodeLine=exPression;
                exPressions.add(exPression);
                exPression = null;
            }
        }else if (c == ';') {
            closed=true;
            lastWord=sb.toString().trim();
            sb.setLength(0);
        } else if (c == ' ') {

        } else if (c == '(') {

            lastWord = sb.toString();
            sb.setLength(0);
            if (lastWord.equals("if")) {//if判断

            } else {//函数
                exPression = new FunctionCodeLine(lastWord);
            }

        } else {
            sb.append(c);
        }
    }
}
