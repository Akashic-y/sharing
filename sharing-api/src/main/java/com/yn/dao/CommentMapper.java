package com.yn.dao;

import java.util.List;

import com.yn.entity.Comment;
import tk.mybatis.mapper.common.Mapper;

public interface CommentMapper extends Mapper<Comment> {
    List<Comment> findAll();

    List<Comment> findByArticle(Integer id);

    void deleteById(Integer id);

    Comment selectById(Integer id);
}