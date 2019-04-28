package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.Article;

import java.util.List;

public interface ArticleService {
    public List<Article> findAllArticle();
    public List<Article> findUserArticle(int userid);
    public Article getArticleById(int id);
    public int createNewArticle(Article article);
    public List<Article> getArticleList();
    public void updateVisitTimes(int id);
}
