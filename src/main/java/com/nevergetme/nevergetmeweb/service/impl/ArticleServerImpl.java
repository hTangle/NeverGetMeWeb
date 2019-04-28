package com.nevergetme.nevergetmeweb.service.impl;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.mapper.ArticleMapper;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServerImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public List<Article> findAllArticle() {
        List<Article> articles=articleMapper.getAllArticle();
        return articles;
    }

    @Override
    public List<Article> findUserArticle(int userid) {
        return articleMapper.getUserArticle(userid);
    }

    @Override
    public Article getArticleById(int id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public int createNewArticle(Article article) {
        return articleMapper.createNewArticle(article);
    }

    @Override
    public List<Article> getArticleList() {
        return articleMapper.getArticleList();
    }

    @Override
    public void updateVisitTimes(int id) {
        articleMapper.updateVisitTimes(id);
    }
}
