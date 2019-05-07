package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

/**
 * Create by Alden He on 2019/5/7
 */
//@Data
public class Comment {
    private Integer id;//主键id
    private int parentId;//父类评论id，用于多级评论与回复
    private int commentUserId;//创建该评论的用户id
    private int responseUserId;//该评论回复的用户
    private int articleId;//
    private String content;
    private String date;

    //private int
}
