package com.nevergetme.nevergetmeweb.service.impl;

import com.nevergetme.nevergetmeweb.bean.ArticleComment;
import com.nevergetme.nevergetmeweb.mapper.ArticleCommentMapper;
import com.nevergetme.nevergetmeweb.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Alden He on 2019/5/7
 */
@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {
    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    @Override
    public List<ArticleComment> getArticleCommentsByArticleId(int id) {
        return articleCommentMapper.getArticleCommentsByArticleId(id);
    }

    @Override
    public int addArticleComment(ArticleComment articleComment) {
        return articleCommentMapper.addArticleComment(articleComment);
    }

}
