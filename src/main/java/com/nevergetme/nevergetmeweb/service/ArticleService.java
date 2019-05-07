package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.bean.Tags;

import java.util.List;

public interface ArticleService {
    List<Article> findAllArticle();
    List<Article> findUserArticle(int userid);
    Article getArticleByUserIDAndID(int id,int authorId);
    int updateArticleByIdAndUserid(Article article);
    Article getArticleById(int id);
    int createNewArticle(Article article,List<Integer> tagid);
    List<Article> getArticleList(int pageNum);
    void updateVisitTimes(int id);
    List<Tags> getAllTags();
    void setArticleTags(int articleId,int tagid);
}
