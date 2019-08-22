package com.yn.dao;

import java.util.List;

import com.yn.entity.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

	List<Comment> findAll();

	List<Comment> findByArticle(Integer id);
}