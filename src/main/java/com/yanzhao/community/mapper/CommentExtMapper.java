package com.yanzhao.community.mapper;

import com.yanzhao.community.model.Comment;
import com.yanzhao.community.model.CommentExample;
import com.yanzhao.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}