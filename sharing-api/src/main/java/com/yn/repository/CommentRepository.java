package com.yn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yn.entity.Article;
import com.yn.entity.Comment;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByArticleAndLevelOrderByCreateDateDesc(Article a, String level);


}
