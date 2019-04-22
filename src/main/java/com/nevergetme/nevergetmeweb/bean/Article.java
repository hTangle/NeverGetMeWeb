package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Article implements Serializable {
    private static final long serialVersionUID = 2L;
    private int id;//主键
    private int articleId;//文章id
    private String author;//作者id
    private String originalAuthor;//原作者
    private String articleTitle;//标题
    private String articleContent;//内容
    private String articleTags;//标签
    private String articleType;//类型
    private String articleCategories;//分类
    private String publishDate;//发表日期
    private String updateDate;//更新日期
    private int articleUrl;//url
    private String articleTabloid;//摘要
    private int likes;//喜欢数
    private int lastArticleId;//上一篇文章id
    private int nextArticleId;//下一篇文章id
}
