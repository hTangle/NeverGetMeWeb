package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.Article;

import java.util.List;

public interface ArticleService {
    public List<Article> findAllArticle();
    public List<Article> findUserArticle(int userid);
    public Article getArticleById(int id);
}
