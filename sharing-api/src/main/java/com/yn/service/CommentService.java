package com.yn.service;

import java.util.List;

import com.yn.entity.Category;
import com.yn.entity.Comment;
import com.yn.vo.CategoryVO;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
public interface CommentService {

    List<Comment> findAll();

    Comment getCommentById(Integer id);

    Integer saveComment(Comment comment);

    void deleteCommentById(Integer id);

    List<Comment> listCommentsByArticle(Integer id);

    Comment saveCommentAndChangeCounts(Comment comment);

    void deleteCommentByIdAndChangeCounts(Integer id);


}
