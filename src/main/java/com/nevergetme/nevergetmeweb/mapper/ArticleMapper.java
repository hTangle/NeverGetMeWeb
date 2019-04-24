package com.nevergetme.nevergetmeweb.mapper;

import com.nevergetme.nevergetmeweb.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ArticleMapper {
    public List<Article> getAllArticle();
    public List<Article> getUserArticle(int userId);
    public Article getArticleById(int id);
    public int createNewArticle(Article article);
    public List<Article> getArticleList();
}
