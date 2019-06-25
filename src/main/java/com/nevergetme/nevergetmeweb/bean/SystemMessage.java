package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Create by Alden He on 2019/6/1
 */
@Data
public class SystemMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int receiver;
    private String content;
    private int sendState;
    private int readState;
    private String createTime;
    private int type;
    private int url;
    private int sender;
    public SystemMessage(){}
    public SystemMessage(String content){
        this.content=content;
    }
    public SystemMessage(int receiver,int sender,String content,int type,int url){
        this.receiver=receiver;
        this.sender=sender;
        this.content=content;
        sendState=0;
        readState=0;
        this.type=type;
        this.url=url;
    }
}
