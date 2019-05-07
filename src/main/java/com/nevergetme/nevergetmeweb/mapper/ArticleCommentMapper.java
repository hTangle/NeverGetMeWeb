package com.nevergetme.nevergetmeweb.mapper;

import com.nevergetme.nevergetmeweb.bean.ArticleComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create by Alden He on 2019/5/7
 */
@Mapper
public interface ArticleCommentMapper {
    List<ArticleComment> getArticleCommentsByArticleId(int id);
    int addArticleComment(ArticleComment articleComment);
}
