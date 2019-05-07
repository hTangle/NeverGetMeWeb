package com.nevergetme.nevergetmeweb.bean;

import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private int authorId;
    private User author;
    private String title;//标题
    private String content;//内容
    private String publishDate;//发表日期
    private String updateDate;//更新日期
    private int likes;//喜欢数
    private String shortcut;
    private int visitTimes;
    private int isOriginal;
    List<Tags> tagsList;

    public Article() {
    }

    public Article(int id, String title, String content, String publishDate, String updateDate, int likes, int user_id, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.updateDate = updateDate;
        this.likes = likes;
        this.authorId = user_id;
        this.author = new User(user_id, username);
    }

    public Article(int id, User user, String title, String content, String publishDate, String updateDate, int likes) {
        this(id, user, title, publishDate, updateDate, likes);
        this.content = content;
    }

    public Article(int id, User user, String title, String publishDate, String updateDate, int likes) {
        this.id = id;
        this.author = user;
        this.title = title;
        this.publishDate = publishDate;
        this.updateDate = updateDate;
        this.likes = likes;
    }

    public Article(int id,int authorId, String title, String content) {
        this.id=id;
        this.title = title;
        this.authorId = authorId;
        this.content = content;
        //this.shortcut = ContentUtility.getArticleShortCut(content);
    }

    public Article(int authorId, String title, String content, String shortcut,int isOriginal) {
        this.title = title;
        this.authorId = authorId;
        this.content = content;
        this.shortcut = ContentUtility.getArticleShortCut(shortcut);
        this.isOriginal=isOriginal;
    }
}
