package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.bean.PublishDateStatistical;
import com.nevergetme.nevergetmeweb.bean.Search;
import com.nevergetme.nevergetmeweb.bean.Tags;

import java.util.List;

public interface ArticleService {
    List<Article> findAllArticleWithoutUserAndTags();
    List<Article> findAllArticle();
    List<Article> findUserArticle(int userid);
    Article getArticleByUserIDAndID(int id,int authorId);
    Article getArticleByArticleId(int id);
    int updateArticleByIdAndUserid(Article article);
    Article getArticleById(int id);
    int createNewArticle(Article article,List<Integer> tagid);
    List<Article> getArticleList(int pageNum);
    List<Article> getArticleList();
    List<Article> getArticleListByTagId(int tagid);
    //模糊查找 title的相关内容
    List<Article> getArticleListByTitle(String contents);

    void updateVisitTimes(int id);
    List<Tags> getAllTags();
    void setArticleTags(int articleId,int tagid);
    List<PublishDateStatistical> getStatisticalDataOfPublishDate();
    void updateArticleIsStick(int id,int isStick);
    void updateArticleCover(Article article);

    //搜索记录相关
    List<Search> getSearchHistory();
    int addSearchHistory(Search search);
}
