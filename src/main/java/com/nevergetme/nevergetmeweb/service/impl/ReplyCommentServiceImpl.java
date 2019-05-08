package com.nevergetme.nevergetmeweb.service.impl;

import com.nevergetme.nevergetmeweb.bean.ReplyComment;
import com.nevergetme.nevergetmeweb.mapper.ReplyCommentMapper;
import com.nevergetme.nevergetmeweb.service.ReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by Alden He on 2019/5/8
 */
@Service
public class ReplyCommentServiceImpl implements ReplyCommentService {
    @Autowired
    private ReplyCommentMapper replyCommentMapper;
    @Override
    public int addReplyCommentMapper(ReplyComment replyComment) {
        return replyCommentMapper.addReplyCommentMapper(replyComment);
    }
}
