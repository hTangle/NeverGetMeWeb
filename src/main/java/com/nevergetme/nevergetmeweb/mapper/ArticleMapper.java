package com.nevergetme.nevergetmeweb.mapper;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.bean.PublishDateStatistical;
import com.nevergetme.nevergetmeweb.bean.Tags;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> findAllArticleWithoutUserAndTags();
    List<Article> getAllArticle();
    List<Article> getUserArticle(int userId);
    Article getArticleById(int id);
    int createNewArticle(Article article);
    List<Article> getArticleList();
    void updateVisitTimes(int id);
    int getTotalArticleCount();
    List<Tags> getTagsByArticleId(int id);
    List<Tags> getAllTags();
    void setArticleTags(int articleId,int tagid);
    int updateArticleByIdAndUserid(Article article);
    List<Article> getArticleListByTagId(int tagid);
    List<PublishDateStatistical> getStatisticalDataOfPublishDate();
    void updateArticleIsStick(int id,int isStick);
    void updateArticleCover(Article article);
}
