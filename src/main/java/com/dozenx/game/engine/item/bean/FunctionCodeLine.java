package com.dozenx.game.engine.item.bean;

import com.dozenx.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:34 2018/7/9
 * @Modified By:
 */
public class FunctionCodeLine extends  CodeLine{
    public FunctionCodeLine(String name){
        this.name =name;
    }
    String name ;
    List<String> args=new ArrayList<>();
    public void push(char c){

        if (exPression != null) {

            exPression.push(c);
            if (exPression.closed) {
                exPressions.add(exPression);
                exPression = null;
            }
        } else if (c == ';') {
            closed=true;
            lastWord=sb.toString().trim();
            sb.setLength(0);
        }else  if (c == '"') {
            if(stringStart){
                lastWord = sb.toString();
                args.add(lastWord);
                sb.setLength(0);
                lastWord=null;
                stringStart = false;
                //sb.append(c);
            }else {
                stringStart = true;
                sb.setLength(0);
                //sb.append(c);
            }
        }else if (c == ')') {
            closed=true;
            lastWord=sb.toString().trim();
            sb.setLength(0);
            if(StringUtil.isNotEmpty(lastWord)){
                args.add(lastWord);
            }

        }else if (c == ' ') {

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
