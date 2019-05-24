package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Create by Alden He on 2019/5/23
 */
@Data
public class Search implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String content;
    private int times;
    public Search(){}
    public Search(String content,int times){
        this.content=content;
        this.times=times;
    }
}
