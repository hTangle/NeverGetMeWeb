package com.nevergetme.nevergetmeweb.mapper;

import com.nevergetme.nevergetmeweb.bean.ReplyComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create by Alden He on 2019/5/8
 */
@Mapper
public interface ReplyCommentMapper {
    int addReplyCommentMapper(ReplyComment replyComment);
    List<ReplyComment> getReplyCommentsByCommentId(int id);
}
