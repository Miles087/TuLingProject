package com.example.server.tulingtest;

/**
 * Created by Server on 2018/1/5.
 */

public class MessageData {
    public static final int SEND = 1;
    public static final int RECEIVE = 2;
    private String strMessage;
    private int flag = 1;

    public MessageData(String strMessage){
        setStrMessage(strMessage);
    }

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
