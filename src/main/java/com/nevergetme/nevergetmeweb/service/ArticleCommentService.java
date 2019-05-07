package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.ArticleComment;

import java.util.List;

/**
 * Create by Alden He on 2019/5/7
 */
public interface ArticleCommentService {
    List<ArticleComment> getArticleCommentsByArticleId(int id);
    int addArticleComment(ArticleComment articleComment);
}
