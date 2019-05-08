package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

/**
 * Create by Alden He on 2019/5/8
 * comment
 *   reply1(@comment)
 *   reply2(@reply1)--这条回复@了relpy1，但是也要发通知给comment和article
 */
/*
create table ReplyComment(
id int(11) not null auto_increment primary key,
articleId int(11) not null,
commentId int(11) not null,
replyUserId int(11) not null,
repliedUserId int(11) not null,
content varchar(200) not null,
createTime timestamp not null default current_timestamp
)Engine=InnoDB auto_increment=100000 default charset=utf8;
 */
@Data
public class ReplyComment {
    private Integer id;
    private int articleId;
    private int commentId;
    //private int commentUserId;//
    private int replyUserId;//创建这条回复的id
    private User user;//读取评论的时候需要读取这个user信息
    private int repliedUserId;//这条回复的@人的id
    private String content;
    private String createTime;
    public ReplyComment(){}
    public ReplyComment(int articleId,int commentId,int replyUserId,int repliedUserId,String content){
        this.articleId=articleId;
        this.commentId=commentId;
        this.replyUserId=replyUserId;
        this.repliedUserId=repliedUserId;
        this.content=content;
    }
}
