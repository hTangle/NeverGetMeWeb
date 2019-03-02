package com.nevergetme.nevergetmeweb.bean;

import java.io.Serializable;

public class Article implements Serializable {
    private static final long serialVersionUID = 2L;
    private int id;
    private String title;
    private String content;
    private int userid;
    private String updateTime;
    public int getId(){return id;}
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public int getUserid(){return userid;}
    public String getUpdateTime(){return updateTime;}
    public void setId(int id){this.id=id;}
    public void setTitle(String title){this.title=title;}
    public void setContent(String content){this.content=content;}
    public void setUserid(int userid){this.userid=userid;}
    public void setUpdateTime(String updateTime){this.updateTime=updateTime;}
}
