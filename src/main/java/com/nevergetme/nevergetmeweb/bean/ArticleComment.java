package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

/**
 * Create by Alden He on 2019/5/7
 */
@Data
public class ArticleComment {
    private Integer id;//主键id
    private int commentUserId;//创建该评论的用户id
    private User user;
    private int articleId;//评论的文章id
    private String content;//评论内容
    private String createTime;//评论的日期
    public ArticleComment(){}
    public ArticleComment(int commentUserId,int articleId,String content){
        this.articleId=articleId;
        this.commentUserId=commentUserId;
        this.content=content;
    }
}
